package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.DoctorNotFoundException;
import com.example.demo.persistence.entity.Doctor;
import com.example.demo.persistence.repository.DoctorRepository;

import lombok.AllArgsConstructor;

@Service
public class DoctorServiceImpl implements DoctorService {

	//
	// Dependent class which needs to be mocked
	// 
	private DoctorRepository doctorRepository;
	
	@Autowired
	public DoctorServiceImpl(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Doctor doesUserExists(String email) throws DoctorNotFoundException {
		// 
		// findByEmail method which needs to be pre-programmed
		//
		List<Doctor> doctors = doctorRepository.findByEmail(email);
		if (doctors.isEmpty())
			throw new DoctorNotFoundException("User does not exists in the database.");
		return doctors.get(0);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Doctor> findByLocationAndSpeciality(String location, String speciality) {
		return doctorRepository.findByLocationAndSpeciality(location, speciality);
	}

}
