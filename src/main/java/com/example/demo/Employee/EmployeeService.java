package com.example.demo.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String registerEmployee(SignupRequestDTO request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            return "Email already in use!";
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Employee employee = new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getPosition(),
                request.getEmail(),
                encodedPassword
        );

        employeeRepository.save(employee);
        return "Employee registered successfully!";
    }

    public String loginEmployee(LoginDTO request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail());
        if (employee == null) {
            return "Email not found!";
        }

        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }
}
