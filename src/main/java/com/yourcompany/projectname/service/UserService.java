package com.yourcompany.projectname.service;

import com.yourcompany.projectname.dto.UserInput;
import com.yourcompany.projectname.model.User;
import com.yourcompany.projectname.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(UserInput userInput) {
        User newUser = new User();
        newUser.setFullname(userInput.getFullname());
        newUser.setEmail(userInput.getEmail());
        newUser.setPhone(userInput.getPhone());
        // Trong thực tế, bạn nên mã hóa mật khẩu ở đây
        newUser.setPassword(userInput.getPassword());
        return userRepository.save(newUser);
    }

    @Transactional
    public User updateUser(Long id, UserInput userInput) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        existingUser.setFullname(userInput.getFullname());
        existingUser.setEmail(userInput.getEmail());
        existingUser.setPhone(userInput.getPhone());
        if (userInput.getPassword() != null && !userInput.getPassword().isEmpty()) {
            existingUser.setPassword(userInput.getPassword());
        }
        return userRepository.save(existingUser);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
