package com.example.vmall.Sercurity;

import com.example.vmall.Entity.User;
import com.example.vmall.Enums.Role;
import com.example.vmall.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class ApplicationInitConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationInitConfig.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRoles(roles);
                userRepository.save(user);
                logger.info("admin user has been created");
            }else{
                logger.info("admin user has already been created");
            }
        };
    }
}
