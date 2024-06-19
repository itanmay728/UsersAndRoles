package com.UsersAndRoles.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.UsersAndRoles.model.Users;

public interface UsersRepo extends JpaRepository<Users, Long> {

	@Query("select a from Users a where a.userName = :userName")
	Optional<Users> findOneByUserNameIgnoreCase(String userName);
}
