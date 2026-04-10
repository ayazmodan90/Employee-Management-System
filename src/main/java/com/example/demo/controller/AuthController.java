package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")   // 🔥 IMPORTANT (URL fix)
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/index")
    public String display() {
        return "index";
    }

    // ================= REGISTER =================
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {

        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null) {
            model.addAttribute("error", "Email already exists!");
            return "auth/register";
        }

        user.setRole("USER");
        userService.saveUser(user);

        return "redirect:/auth/mlogin";
    }

    // ================= LOGIN =================
    @GetMapping("/mlogin")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user,
                            HttpSession session,
                            Model model) {

        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null &&
                existingUser.getPassword().equals(user.getPassword())) {

            session.setAttribute("loggedInUser", existingUser);

            if ("ADMIN".equals(existingUser.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/auth/dashboard";
            }
        }

        model.addAttribute("error", "Invalid Credentials");
        return "auth/login";
    }

    // ================= USER DASHBOARD =================
    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("username", user.getName());

        return "auth/dashboard";  // 🔥 your dashboard page
    }

    // ================= PROFILE =================
    @GetMapping("/profile")
    public String profileDashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";  // 🔥 FIXED
        }

        model.addAttribute("user", user); // 🔥 IMPORTANT

        return "auth/profile"; // 🔥 templates/profile.html
    }

    // ================= LOGOUT =================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
    
 // Forgot Password Page
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    // Verify Email
    @PostMapping("/forgot-password")
    public String verifyEmail(@RequestParam String email, Model model) {

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Email not found!");
            return "auth/forgot-password";
        }

        model.addAttribute("email", email);
        return "auth/reset-password";
    }

    // Reset Password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String password,
                                Model model) {

        User user = userService.findByEmail(email);

        if (user != null) {
            user.setPassword(password);
            userService.saveUser(user);
        }

        model.addAttribute("msg", "Password updated successfully!");
        return "auth/login";
    }
    
 // ================= OPEN UPDATE PAGE =================
    @GetMapping("/profile/edit")
    public String editProfilePage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);

        return "auth/edit-profile";
    }

    // ================= SAVE UPDATED PROFILE =================
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User user,
                                HttpSession session) {

        User existingUser = (User) session.getAttribute("loggedInUser");

        if (existingUser == null) {
            return "redirect:/auth/login";
        }

        // update values
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        userService.saveUser(existingUser);

        session.setAttribute("loggedInUser", existingUser);

        return "redirect:/auth/profile?updated";
    }
    
    @GetMapping("/profile/update")
    public String redirectUpdate() {
        return "redirect:/auth/profile";
    }
    
    
}