package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        user.setPosition(Users.Position.valueOf(request.getPosition().toUpperCase()));
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setContactNumber(request.getContactNumber());
        user.setSex(request.getSex());

        userRepository.save(user);
        return "Employee registered successfully!";
    }

    public Optional<Users> loginEmployee(LoginDTO request) {
        Optional<Users> userOptional = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        Users user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

}