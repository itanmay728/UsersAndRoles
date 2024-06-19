package com.UsersAndRoles.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.UsersAndRoles.model.Roles;
import com.UsersAndRoles.model.Users;
import com.UsersAndRoles.repo.RolesRepo;
import com.UsersAndRoles.repo.UsersRepo;
import com.UsersAndRoles.service.UsersService;

@Service
public class UserServiceImpl implements UsersService,  UserDetailsService {

	@Autowired
	private UsersRepo usersRepo;
	
	@Autowired
	private RolesRepo rolesRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void saveUserByDefaultRole(Users users) {
		
		String password = passwordEncoder.encode(users.getPassword());
		
		users.setPassword(password);
		
		Roles roles = rolesRepo.findByRole("User");
		users.addRole(roles);
		usersRepo.save(users);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Users> optionalUser = usersRepo.findOneByUserNameIgnoreCase(username);
		
		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException("User not found");
		}
		
		Users user = optionalUser.get();
		
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		Set<Roles> roles = user.getRoles();
		
		
		
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		
		for(Roles r : roles) {
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+r.getRole()));
		}
		
		grantedAuthorities.addAll(simpleGrantedAuthorities);
		
		return new User(user.getUserName(), user.getPassword(), grantedAuthorities);
	}

	@Override
	public List<Users> getAllUsers() {
		
		return usersRepo.findAll();
	}

	@Override
	public Users getUserByUserId(Long id) {
		
		return usersRepo.findById(id).get();
	}

	@Override
	public List<Roles> getAllRoles() {
		
		return rolesRepo.findAll();
	}

	@Override
	public void saveEditedUser(Users users) {
		String password = passwordEncoder.encode(users.getPassword());
		
		users.setPassword(password);
		usersRepo.save(users);
		
	}
	
	

}
