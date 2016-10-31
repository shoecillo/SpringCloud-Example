package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.entity.UserEntity;
import com.sh.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository repo;
	
	public UserEntity getUser(String user)
	{
		return repo.findByUsername(user);
	}
	
	public List<UserEntity> getUserList()
	{
		return repo.findAll();
	}
}
