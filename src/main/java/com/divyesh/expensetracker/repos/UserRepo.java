package com.divyesh.expensetracker.repos;

import com.divyesh.expensetracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
    User findByUsername(String username);

}
