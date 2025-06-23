package com.pooji.hospitalmanagement.controller;

import com.pooji.hospitalmanagement.entities.Doctor;
import com.pooji.hospitalmanagement.entities.User;
import com.pooji.hospitalmanagement.repositories.DoctorRepository;
import com.pooji.hospitalmanagement.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final DoctorRepository doctorRepo;

    @GetMapping("/")
    public String home() {
        return "index"; // Home Page
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String role,
                               @RequestParam(required = false) String specialization) {
        User user = new User(null, name, email, password, role);
        userRepo.save(user);

        if (role.equalsIgnoreCase("DOCTOR")) {
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setSpecialization(specialization);
            doctorRepo.save(doctor);
        }

        return "redirect:/login?success";

    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        User user = userRepo.findByEmailAndPassword(email, password);

        if (user == null) {
            // 🔴 Yahan Model nahī, RedirectAttributes use karo
            redirectAttributes.addFlashAttribute("error",
                    "User is not registered, please create an account.");
            return "redirect:/login";
        }

        // ✅ Successful login
        session.setAttribute("user", user);

        switch (user.getRole().toUpperCase()) {
            case "ADMIN":   return "redirect:/admin/dashboard";
            case "DOCTOR":  return "redirect:/doctor/dashboard";
            default:        return "redirect:/patient/dashboard";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
