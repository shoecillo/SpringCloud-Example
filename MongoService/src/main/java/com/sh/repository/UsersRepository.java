package com.sh.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sh.entity.UserEntity;

public interface UsersRepository extends MongoRepository<UserEntity, String>  
{
	public UserEntity findByUsername(String username);
	
} 
