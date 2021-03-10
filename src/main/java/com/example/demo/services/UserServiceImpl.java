package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	//
	// Dependent class which needs to be mocked
	// 
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public User doesUserExists(String email) throws UserNotFoundException {
		// 
		// findByEmail method which needs to be pre-programmed
		//
		List<User> users = userRepository.findByEmail(email);
		if (users.isEmpty())
			throw new UserNotFoundException("User does not exists in the database.");
		return users.get(0);
	}

}
