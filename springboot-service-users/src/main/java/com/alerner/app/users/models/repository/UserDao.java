package com.alerner.app.users.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.alerner.app.commons.users.models.entity.User;

@RepositoryRestResource(path="users")
public interface UserDao extends PagingAndSortingRepository<User, Long>
{
	@RestResource(path="find-username")
	User findByUsername(@Param("username") String username);	 
}
