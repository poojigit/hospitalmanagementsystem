package com.pooji.hospitalmanagement.controller;


import com.pooji.hospitalmanagement.entities.Appointment;
import com.pooji.hospitalmanagement.entities.Doctor;
import com.pooji.hospitalmanagement.entities.User;
import com.pooji.hospitalmanagement.repositories.AppointmentRepository;
import com.pooji.hospitalmanagement.repositories.DoctorRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final DoctorRepository doctorRepo;
    private final AppointmentRepository appointmentRepo;

    // ✅ Patient Dashboard
    @GetMapping("/patient/dashboard")
    public String dashboard() {
        return "patient-dashboard";
    }

    // ✅ Appointment Booking Form Page
    @GetMapping("/patient/book")
    public String bookAppointmentForm(Model model) {
        model.addAttribute("doctors", doctorRepo.findAll());
        return "book-appointment";
    }

    // ✅ Save Appointment with Slot Check
    @PostMapping("/patient/book")
    public String saveAppointment(@RequestParam String date,
                                  @RequestParam String time,
                                  @RequestParam String details,
                                  @RequestParam Long doctorId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);

        // 🔒 Check for slot clash
        Appointment existing = appointmentRepo.findByDoctorAndDateAndTime(doctor, date, time);
        if (existing != null) {
            redirectAttributes.addFlashAttribute("error", "Appointment cannot be booked! Slot is not available.");
            return "redirect:/patient/dashboard";
        }

        // ✅ Book appointment
        Appointment appointment = new Appointment();
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setDetails(details);
        appointment.setPatient(user);
        appointment.setDoctor(doctor);

        appointmentRepo.save(appointment);

        redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
        return "redirect:/patient/dashboard";
    }

    // ✅ View My Appointments
    @GetMapping("/patient/appointments")
    public String viewMyAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Appointment> myAppointments = appointmentRepo.findByPatient(user);
        model.addAttribute("appointments", myAppointments);
        return "patient-appointments";
    }

    // ✅ Cancel Appointment (optional)
    @GetMapping("/patient/appointment/delete/{id}")
    public String cancelAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        appointmentRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Appointment cancelled successfully!");
        return "redirect:/patient/appointments";
    }

}
