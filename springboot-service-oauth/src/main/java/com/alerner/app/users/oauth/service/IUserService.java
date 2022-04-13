package com.alerner.app.users.oauth.service;

import com.alerner.app.commons.users.models.entity.User;

public interface IUserService 
{
	
	public User findByUsername(String username);

}
