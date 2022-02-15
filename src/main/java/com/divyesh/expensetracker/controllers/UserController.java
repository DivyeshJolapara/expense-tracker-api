package com.divyesh.expensetracker.controllers;

import com.divyesh.expensetracker.entities.User;
import com.divyesh.expensetracker.models.HttpStatusResponse;
import com.divyesh.expensetracker.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User u){
        try {

            User check = userRepo.findByUsername(u.getUsername());
            if(check!=null){
                return ResponseEntity.status(400).body(new HttpStatusResponse(400,"User Already exists."));
            }
            User user = userRepo.save(u);
            return ResponseEntity.status(201).body(user);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(422).build();
    }

}
