package com.UsersAndRoles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.UsersAndRoles.model.Roles;
import com.UsersAndRoles.model.Users;
import com.UsersAndRoles.service.UsersService;

@Controller
public class HomeController {

	@Autowired
	private UsersService usersService;
	
	@GetMapping
	public String getHomePage(Model model) {
		model.addAttribute("pageTitel", "Home");
		return "home";
	}
	
	@GetMapping("/registration")
	public String getRegisterPage(Model model) {
		model.addAttribute("pageTitel", "Registration");
		return "register";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute Users users) {
		usersService.saveUserByDefaultRole(users);
		return "redirect:/registration";
	}
	
	@GetMapping("/dashboard")
	public String getDashboard(Model model) {
		model.addAttribute("Users", usersService.getAllUsers());
		return "dashboard";
		
	}
	
	@GetMapping("/admin/user/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
		
		Users user= usersService.getUserByUserId(id);
		
		List<Roles> listRoles  =  usersService.getAllRoles();
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		return "editUser";
	}
	
	@PostMapping("/saveEditedUser")
	public String saveEditedUser(Users users) {
		
		usersService.saveEditedUser(users);
		return "redirect:/dashboard";
	}
}
