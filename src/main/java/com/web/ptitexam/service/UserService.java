package com.web.ptitexam.service;

import com.web.ptitexam.entity.User;

public interface UserService {
   void registerUser (User user);
   boolean authenticate (String username, String password);
   User findByUsername (String username);
}
