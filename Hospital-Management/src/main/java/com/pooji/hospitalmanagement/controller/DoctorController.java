package com.pooji.hospitalmanagement.controller;

import com.pooji.hospitalmanagement.entities.User;
import com.pooji.hospitalmanagement.entities.Doctor;
import com.pooji.hospitalmanagement.repositories.AppointmentRepository;
import com.pooji.hospitalmanagement.repositories.DoctorRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepo;
    private final AppointmentRepository appointmentRepo;

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard() {
        return "doctor-dashboard";
    }

    @GetMapping("/doctor/schedule")
    public String viewMyAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Doctor doctor = doctorRepo.findByUserId(user.getId());
        model.addAttribute("appointments", appointmentRepo.findByDoctor(doctor));
        return "doctor-appointment";
    }
}
