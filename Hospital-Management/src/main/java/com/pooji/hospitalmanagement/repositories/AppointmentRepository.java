package com.pooji.hospitalmanagement.repositories;

import com.pooji.hospitalmanagement.entities.Appointment;
import com.pooji.hospitalmanagement.entities.Doctor;
import com.pooji.hospitalmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByPatient(User patient);
    Appointment findByDoctorAndDateAndTime(Doctor doctor, String date, String time);

}
