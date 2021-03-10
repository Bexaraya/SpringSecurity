package com.example.demo.persistence.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.persistence.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	@Query("SELECT d FROM Doctor d WHERE (:specialityCode IS NULL OR LOWER(d.specialityCode) = LOWER(:specialityCode)) AND (:location IS NULL OR LOWER(d.location) = LOWER(:location))")
	public List<Doctor> findByLocationAndSpeciality(@Param("location") String location,
			@Param("specialityCode") String specialityCode);
	
	@Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) = LOWER(:firstName)")
	public List<Doctor> findByFirstName(@Param("firstName") String firstName);
	
	@Query("SELECT d FROM Doctor d WHERE LOWER(d.email) = LOWER(:email)")
	public List<Doctor> findByEmail(@Param("email") String email);

}
