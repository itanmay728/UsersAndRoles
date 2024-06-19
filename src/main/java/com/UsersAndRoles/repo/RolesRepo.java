package com.UsersAndRoles.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UsersAndRoles.model.Roles;

public interface RolesRepo extends JpaRepository<Roles, Long>{

	 Roles findByRole(String role);
}
