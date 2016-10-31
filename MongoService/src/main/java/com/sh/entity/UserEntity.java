package com.sh.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class UserEntity 
{
	
	
	@Id
	public String id;
	
	public String username;
	
	public String firstName;
	
	public String lastName;

	public UserEntity() {
		
	}
	
	public UserEntity(String userName,String firstName,String lastName) 
	{
		this.username = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	 @Override
	    public String toString() {
	        return String.format(
	                "Customer[id=%s, username='%s',firstName='%s', lastName='%s']",
	                id,username, firstName, lastName);
	    }
}
