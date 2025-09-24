package com.yourcompany.projectname.controller;

import com.yourcompany.projectname.dto.UserInput;
import com.yourcompany.projectname.model.User;
import com.yourcompany.projectname.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @QueryMapping
    public Optional<User> findUserById(@Argument Long id) {
        return userService.findById(id);
    }

    @MutationMapping
    public User createUser(@Argument("user") UserInput userInput) {
        return userService.createUser(userInput);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument("user") UserInput userInput) {
        return userService.updateUser(id, userInput);
    }

    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }
}
