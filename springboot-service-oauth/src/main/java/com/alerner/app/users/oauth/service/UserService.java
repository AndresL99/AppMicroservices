package com.alerner.app.users.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alerner.app.commons.users.models.entity.User;
import com.alerner.app.users.oauth.clients.UserFeignClient;

import brave.Tracer;
import feign.FeignException;

@Service
public class UserService implements UserDetailsService, IUserService
{

	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		try {
			
		User user = userFeignClient.findByUsername(username);
		
		List<GrantedAuthority>authorities = user.getRoles()
				.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority -> log.info("Role: "+ authority.getAuthority()))
				.collect(Collectors.toList());
		
		log.info("User not identified: " + username);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
		
		} catch (FeignException e) {
			String error = "Login error, doesn´t exist '"+username+"'";
			
			tracer.currentSpan().tag("error.message", error+ ":" + e.getMessage());
			throw new UsernameNotFoundException("Login error, doesn´t exist '"+username+"'");
			
		}
		
	}

	@Override
	public User findByUsername(String username) 
	{
		return userFeignClient.findByUsername(username);
	}

	@Override
	public User update(User user, Long id)
	{
		return userFeignClient.update(user, id);
	}

}
