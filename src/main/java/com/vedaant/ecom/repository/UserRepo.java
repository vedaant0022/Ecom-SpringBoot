package com.vedaant.ecom.repository;

import com.vedaant.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findById(int id);

    @Query
    public User findByEmail(String email);

}
