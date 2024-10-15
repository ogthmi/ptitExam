package com.web.ptitexam.service;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.User;

public interface UserService {
    void registerUser(UserDTO userDTO);

    boolean isUsernameTaken(String username);

    void authenticateRegistration(UserDTO userDTO);

    UserDTO getCurrentUser();

    User findByUsername(String username);
}
