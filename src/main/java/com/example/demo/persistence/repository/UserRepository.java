package com.example.demo.persistence.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.persistence.entity.Doctor;
import com.example.demo.persistence.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT d FROM User d WHERE LOWER(d.email) = LOWER(:email)")
	public List<User> findByEmail(@Param("email") String email);

}
