package com.web.ptitexam.service.implement;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.repository.UserRepository;
import com.web.ptitexam.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Mã hóa mật khẩu
        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setDob(userDTO.getDob());
        user.setGender(userDTO.getGender());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());

        userRepository.save(user); // Lưu vào cơ sở dữ liệu
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        System.out.println("Plain password: " + password);
        System.out.println("Encoded password in DB: " + user.getPassword());
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
