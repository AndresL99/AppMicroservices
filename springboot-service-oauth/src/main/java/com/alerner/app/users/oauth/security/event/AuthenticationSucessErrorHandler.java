package com.alerner.app.users.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.alerner.app.commons.users.models.entity.User;
import com.alerner.app.users.oauth.service.IUserService;

import feign.FeignException;
import feign.FeignException.FeignClientException;

@Component
public class AuthenticationSucessErrorHandler implements AuthenticationEventPublisher {
	private Logger log = LoggerFactory.getLogger(AuthenticationSucessErrorHandler.class);

	@Autowired
	private IUserService iUserService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String message = "Success login: " + user.getUsername();
		System.out.println(message);
		log.info(message);
		User userEntity = iUserService.findByUsername(authentication.getName());
		if(userEntity.getAttempt() !=null && userEntity.getAttempt() > 0)
		{
			userEntity.setAttempt(0);
			iUserService.update(userEntity, userEntity.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.error("Error in login: " + exception.getMessage());

		try {
			User user = iUserService.findByUsername(authentication.getName());
			if (user.getAttempt() == null) {
				user.setAttempt(0);
			}
			log.info("Actual attempt is: "+user.getAttempt());
			user.setAttempt(user.getAttempt()+1);
			
			if(user.getAttempt() >=3)
			{
				log.error(String.format("This user is disabled for many attempts", user.getName()));
				user.setEnabled(false);
			}
			
			iUserService.update(user, user.getId());
		} catch (FeignException e) {
			log.error(String.format("This User doesn't exist.", authentication.getName()));
		}
	}

}
