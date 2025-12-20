package com.example.Advances.Banking.System.repository;


import com.example.Advances.Banking.System.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}