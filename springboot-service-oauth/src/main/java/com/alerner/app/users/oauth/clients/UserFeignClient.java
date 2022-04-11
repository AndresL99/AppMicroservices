package com.alerner.app.users.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alerner.app.commons.users.models.entity.User;

@FeignClient(name="service-users")
public interface UserFeignClient 
{

	@GetMapping("/users/search/find-username")
	public User findByUsername(@RequestParam String username);
}
