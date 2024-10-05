package com.web.ptitexam.service;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.User;

public interface UserService {
   void registerUser (UserDTO userDTO);
   boolean authenticate (String username, String password);
   User findByUsername (String username);
   boolean isUsernameTaken (String username);
}
