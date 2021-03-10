package com.example.demo.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.ServerException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.demo.persistence.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest = null;
		try {
			authRequest = this.getUserNamePasswordAuthenticationToken(request);
		}
		catch (IOException e) {
		}
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private UsernamePasswordAuthenticationToken getUserNamePasswordAuthenticationToken(HttpServletRequest request) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader bufferedReader = null;
		String content = "";
		User sr = null;
		try {
			bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				sb.append(charBuffer, 0, bytesRead);
			}
			content = sb.toString();
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				sr = objectMapper.readValue(content, User.class);
			}
			catch (Throwable t) {
				throw new IOException(t.getMessage(), t);
			}
		}
		catch(IOException ex) {
			throw ex;
		}
		finally {
			if (bufferedReader != null)
			{
				try {
					bufferedReader.close();
				}
				catch(IOException ex) {
					throw ex;
				}
			}
		}
		return new UsernamePasswordAuthenticationToken(sr.getEmail(), sr.getPassword());
	}
	
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
