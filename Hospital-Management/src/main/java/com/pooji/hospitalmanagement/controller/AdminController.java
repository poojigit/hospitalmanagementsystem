package com.pooji.hospitalmanagement.controller;

import com.pooji.hospitalmanagement.repositories.AppointmentRepository;
import com.pooji.hospitalmanagement.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DoctorRepository doctorRepo;
    private final AppointmentRepository appointmentRepo;

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/admin/doctors")
    public String viewDoctors(Model model) {
        model.addAttribute("doctors", doctorRepo.findAll());
        return "view-doctors";
    }

    @GetMapping("/admin/appointments")
    public String viewAppointments(Model model) {
        model.addAttribute("appointments", appointmentRepo.findAll());
        return "view-appointments";
    }
}
