package com.example.demo.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.persistence.entity.ExecutionStatus;
import com.example.demo.persistence.entity.User;
import com.example.demo.providers.CustomAuthenticationProvider;

@Controller
@RequestMapping("/account/*")
public class UserAccountController {

	@Autowired
	CustomAuthenticationProvider authProvider;
	
	@PostMapping("/signup/process")
	public ModelAndView processSignup(ModelMap model, 
			@RequestParam("nickname") String nickname,
			@RequestParam("emailAddress") String emailAddress,
			@RequestParam("password") String password) {
		model.addAttribute("login", true);
		model.addAttribute("nickname", nickname);
		model.addAttribute("message", "Have a great day a head.");
		return new ModelAndView("index", model);
	}
	
	@PostMapping("/login")
	public ExecutionStatus processLogin(@RequestBody User reqUser,
			HttpServletRequest request) {
		Authentication authentication = null;
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(reqUser.getEmail(), reqUser.getPassword());
		try {
			//
			// Delegate authentication check to a custom
			// Authentication provider
			//
			authentication = this.authProvider.authenticate(token);
			
			// Store the authentication object in 
			// SecurityContextHolder
			SecurityContextHolder.getContext().setAuthentication(authentication);
			User user = (User) authentication.getPrincipal();
			user.setPassword(null);
			return new ExecutionStatus("USER_LOGIN_SUCCESSFUL", "Login successful!", user); 
		}
		catch(BadCredentialsException e)
		{
			return new ExecutionStatus("USER_LOGIN_UNSUCCESSFUL", "Username or password is incorrect. Please try again!", null);
		}
	}
	
	@GetMapping("/logout")
	public ExecutionStatus logout(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
		{
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ExecutionStatus("USER_LOGOUT_SUCCESSFUL", "User is logger out", null);
	}
	
}
