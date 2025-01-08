package com.example.olive_Cinema.repository;

import com.example.olive_Cinema.entity.app_user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<app_user, Long> {
    Optional<app_user> findByUsername(String username);
}
