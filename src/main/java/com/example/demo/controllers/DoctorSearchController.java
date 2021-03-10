package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistence.entity.Doctor;
import com.example.demo.services.DoctorService;

@RestController
public class DoctorSearchController {

	@Autowired
	DoctorService docService;
	
	@RequestMapping(value = "/doctors", method = RequestMethod.GET,
			produces = "application/json")
	public List<Doctor> searchDoctor(
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "speciality", required = false) String speciality) {
		return docService.findByLocationAndSpeciality(location, speciality);
	}
}
