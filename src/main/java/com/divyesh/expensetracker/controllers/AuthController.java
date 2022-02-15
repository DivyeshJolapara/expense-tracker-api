package com.divyesh.expensetracker.controllers;

import com.divyesh.expensetracker.cache.SimpleCache;
import com.divyesh.expensetracker.entities.User;
import com.divyesh.expensetracker.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthController {


    class Login{
        public String username;
        public String password;
    }

    @Autowired
    private UserRepo userRepo;
    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody User req){

        User user = userRepo.findByUsernameAndPassword(req.getUsername(), req.getPassword());
        if(user!=null){
            SimpleCache sc = SimpleCache.getInstance();
            String token = java.util.UUID.randomUUID().toString();


            sc.setCacheUser(token,user);

            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).build();
    }
    @PostMapping("/logout")
    public void logout(@RequestHeader Map<String,String> req){
        String token = req.get("token");
        SimpleCache.getInstance().removeCacheUser(token);
    }


}
