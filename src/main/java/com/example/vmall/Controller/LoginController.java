package com.example.vmall.Controller;


import com.example.vmall.Entity.User;
import com.example.vmall.Repository.UserRepository;
import com.example.vmall.Sercurity.JwtUtil;
import com.example.vmall.Service.UserService;
import com.example.vmall.dto.Request.LoginRequest;
import com.example.vmall.dto.Respone.LoginRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean authenticated = userService.authenticate(user.getUsername(), request.getPassword());

            if (authenticated) {
                String token = jwtUtil.generateToken(user);
                System.out.println("Generated JWT Token: " + token);

                return ResponseEntity.ok(new LoginRespone("Login successful", token));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginRespone("Invalid credentials", null));
    }

}