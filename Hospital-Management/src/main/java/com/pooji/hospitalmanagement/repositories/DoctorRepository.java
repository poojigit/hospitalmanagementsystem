package com.pooji.hospitalmanagement.repositories;

import com.pooji.hospitalmanagement.entities.Appointment;
import com.pooji.hospitalmanagement.entities.Doctor;
import com.pooji.hospitalmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByUserId(Long userId);

}
