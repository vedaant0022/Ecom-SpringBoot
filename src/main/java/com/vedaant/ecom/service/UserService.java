package com.vedaant.ecom.service;

import com.vedaant.ecom.model.User;
import com.vedaant.ecom.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserRepo repo;

    public User addUser(User user) {
        return repo.save(user);
    }

    public User checkUser(String email){
        return repo.findByEmail(email);
    }

}
