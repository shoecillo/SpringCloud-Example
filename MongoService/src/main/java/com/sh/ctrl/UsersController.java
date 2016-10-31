package com.sh.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.service.UsersService;

@RestController
public class UsersController 
{
	@Autowired
	private UsersService serv;
	
	@RequestMapping("/{user}")
	public String getUser(@PathVariable String user)
	{
		return serv.getUser(user).firstName;
	}
	
	@RequestMapping("/list")
	public int getAllUsers()
	{
		return serv.getUserList().size();
	}
	
}
