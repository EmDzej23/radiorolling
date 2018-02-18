package com.nevreme.rolling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nevreme.rolling.dao.UserDao;
import com.nevreme.rolling.model.User;


@Service("userService")
public class UserService extends AbstractService<User, Long> {
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	public UserService(UserDao adminDao) {
		super(adminDao);
	}
	
	public User findUserByEmail(String email) {
		return dao.findUserByEmail(email);
	}

	

}
