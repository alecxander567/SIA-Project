package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String registerEmployee(SignupRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already in use!";
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Users user = new Users();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPosition(request.getPosition());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setContactNumber(request.getContactNumber());
        user.setSex(request.getSex());

        String role = switch (request.getPosition().toLowerCase()) {
            case "admin" -> "ADMIN";
            case "rider", "delivery guy" -> "DELIVERY";
            default -> "EMPLOYEE";
        };
        user.setRole(role);

        userRepository.save(user);
        return "Employee registered successfully!";
    }

    public String loginEmployee(LoginDTO request) {
        Users user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return "Email not found!";
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }
}