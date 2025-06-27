package com.example.vmall.Service;

import com.example.vmall.Entity.User;
import com.example.vmall.Enums.Role;
import com.example.vmall.Repository.UserRepository;
import com.example.vmall.dto.Request.UserCreate;
import com.example.vmall.dto.Request.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Phương thức xác thực người dùng khi đăng nhập
    public boolean authenticate(String userName, String password) {
        Optional<User> userOpt = userRepository.findByUsername(userName);
        return userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword());
    }

    public User Create(UserCreate request){
        User user = new User();
        user.setUsername(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        userRepository.save(user);
        return user;
    }

   // @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("404 Not Found"+ id));
    }
    public User Update(Integer id, UserUpdate request){
        User user = getUserById(id);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }
    public void delete(Integer id){
        User user = getUserById(id);
        userRepository.deleteById(id);
    }




}
