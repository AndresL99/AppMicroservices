package com.alerner.app.zuul.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception 
	{
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception 
	{
		http.authorizeRequests().antMatchers("/api/security/oauth/**")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/api/products/list", "/api/items/list","/api/users")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/api/products/detail/{id}","/api/items/detail/{id}/size/{size}","/api/users/users/{id}")
		.hasAnyRole("ADMIN", "USER")
		.antMatchers("/api/products/**", "/api/items/**", "/api/users/**").hasRole("ADMIN")
		.anyRequest().authenticated();
	}
	
	@Bean
	public JwtTokenStore tokenStore() 
	{
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() 
	{
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("secret_code_oooo");
		return accessTokenConverter;
	}
}