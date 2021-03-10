package com.example.demo.services;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.persistence.entity.User;

public interface UserService {

	User doesUserExists(String firstName) throws UserNotFoundException;
}
