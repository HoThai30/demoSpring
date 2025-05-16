package com.example.vmall.Controller;


import com.example.vmall.Entity.User;
import com.example.vmall.Service.UserService;
import com.example.vmall.dto.Request.UserCreate;
import com.example.vmall.dto.Request.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User create(@RequestBody UserCreate request){
        return userService.Create(request);

    }
    @GetMapping("/get")
    public List<User> get(){
        return userService.getAllUsers();
    }
    @GetMapping("/getById")
    public User getById(Integer id){
        return userService.getUserById(id);
    }
    @PutMapping("update")
    public User update(int id,@RequestBody UserUpdate request){
        return userService.Update(id,  request);
    }
    @DeleteMapping("delete")
    String deleteById(int id){
        userService.delete(id);
        return "delete success" ;
    }
}
