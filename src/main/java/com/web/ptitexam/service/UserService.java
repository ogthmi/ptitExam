package com.web.ptitexam.service;

import com.web.ptitexam.dto.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO);

    UserDTO authenticateAndGetUser(String username, String password);

    UserDTO findByUsername(String username);

    boolean isUsernameTaken(String username);

    UserDTO getCurrentUser();
}
