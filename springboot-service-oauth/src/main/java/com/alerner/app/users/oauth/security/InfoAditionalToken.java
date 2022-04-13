package com.alerner.app.users.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.alerner.app.commons.users.models.entity.User;
import com.alerner.app.users.oauth.service.IUserService;

@Component
public class InfoAditionalToken implements TokenEnhancer
{

	@Autowired
	private IUserService iUserService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) 
	{
		Map<String, Object> info = new HashMap<String, Object>();
		User user = iUserService.findByUsername(authentication.getName());
		info.put("name", user.getName());
		info.put("lastName", user.getLastName());
		info.put("email", user.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
