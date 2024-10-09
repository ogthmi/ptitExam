package com.web.ptitexam.service;

import com.web.ptitexam.dto.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO);

    boolean isUsernameTaken(String username);

    UserDTO getCurrentUser();
}
