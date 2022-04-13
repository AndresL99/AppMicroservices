package com.alerner.app.users.oauth.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.alerner.app.commons.users.models.entity.User;

public interface IUserService 
{
	
	public User findByUsername(String username);
	
	public User update(User user, Long id);

}