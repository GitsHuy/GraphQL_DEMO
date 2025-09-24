package com.yourcompany.projectname.repository;

import com.yourcompany.projectname.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
