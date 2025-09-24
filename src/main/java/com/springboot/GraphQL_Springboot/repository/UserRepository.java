package com.springboot.GraphQL_Springboot.repository;

import com.springboot.GraphQL_Springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}