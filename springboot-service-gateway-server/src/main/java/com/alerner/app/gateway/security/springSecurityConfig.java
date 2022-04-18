package com.alerner.app.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class springSecurityConfig 
{
	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http)
	{
		return http.authorizeExchange()
				.pathMatchers("/api/security/oauth/**").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/products/list",
						"/api/items/list", "/api/users/users",
						"/api/items/detail/{id}/size/{size}",
						"/api/products/detail/{id}").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/users/users/{id}").hasAnyRole("ADMIN","USER")
				.pathMatchers("/api/products/**, /api/items/**, /api/users/users/**").hasRole("ADMIN")
				.anyExchange().authenticated()
				.and().csrf().disable()
				.build();
	}
}
