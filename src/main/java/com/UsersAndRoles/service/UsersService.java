package com.UsersAndRoles.service;

import java.util.List;

import com.UsersAndRoles.model.Roles;
import com.UsersAndRoles.model.Users;

public interface UsersService {
	
	public void saveUserByDefaultRole(Users users);

	public List<Users> getAllUsers();
	
	public Users getUserByUserId(Long id);
	
	public List<Roles> getAllRoles();
	
	public void saveEditedUser(Users users);
}
