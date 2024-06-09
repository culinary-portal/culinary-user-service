package com.culinary.userservice.user.repository;


import com.culinary.userservice.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT c FROM User c WHERE c.email = :email")
    Optional<User> findByEmail(@Param(value = "email") String email);

}
