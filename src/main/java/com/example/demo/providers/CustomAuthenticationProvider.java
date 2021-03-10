package com.example.demo.providers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.exception.DoctorNotFoundException;
import com.example.demo.persistence.entity.Doctor;
import com.example.demo.persistence.entity.User;
import com.example.demo.services.DoctorService;
import com.example.demo.services.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		User doctor = null;
		
		try {
			doctor = this.userService.doesUserExists(username);
		}
		catch (DoctorNotFoundException e) {}
		
		if (doctor == null || !doctor.getEmail().equalsIgnoreCase(username)) {
			throw new BadCredentialsException("Username not found.");
		}
		
		if (!password.equals(doctor.getPassword())) {
			throw new BadCredentialsException("Wrong password.");
		}
		
		Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		return new UsernamePasswordAuthenticationToken(doctor, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
