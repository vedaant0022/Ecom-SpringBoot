package com.vedaant.ecom.repository;

import com.vedaant.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    @Query
    public User findByEmail(String email);

}
