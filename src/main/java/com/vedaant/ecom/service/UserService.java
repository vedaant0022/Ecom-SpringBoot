package com.vedaant.ecom.service;

import com.vedaant.ecom.model.User;
import com.vedaant.ecom.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.vedaant.ecom.Config.SecurityConfig;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserRepo repo;
    SecurityConfig config;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public User checkUser(String email){
        return repo.findByEmail(email);
    }

}
