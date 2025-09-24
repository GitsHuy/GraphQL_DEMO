package com.springboot.GraphQL_Springboot.controller;

import com.springboot.GraphQL_Springboot.dto.UserInput;
import com.springboot.GraphQL_Springboot.model.Category;
import com.springboot.GraphQL_Springboot.model.User;
import com.springboot.GraphQL_Springboot.repository.CategoryRepository;
import com.springboot.GraphQL_Springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @QueryMapping
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @QueryMapping
    public Optional<User> findUserById(@Argument Long id) {
        return userRepository.findById(id);
    }

    @MutationMapping
    @Transactional
    public User createUser(@Argument UserInput user) {
        // Trong thực tế, bạn nên mã hóa mật khẩu ở đây
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        var newUser = new User();
        newUser.setFullname(user.getFullname());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setPassword(user.getPassword()); // Không mã hóa để demo
        return userRepository.save(newUser);
    }

    @MutationMapping
    @Transactional
    public User updateUser(@Argument Long id, @Argument UserInput user) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingUser.setFullname(user.getFullname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        return userRepository.save(existingUser);
    }

    @MutationMapping
    @Transactional
    public boolean deleteUser(@Argument Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    @Transactional
    public Category addUserToCategory(@Argument Long userId, @Argument Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        user.getCategories().add(category);
        category.getUsers().add(user);

        userRepository.save(user);
        return categoryRepository.save(category);
    }
}