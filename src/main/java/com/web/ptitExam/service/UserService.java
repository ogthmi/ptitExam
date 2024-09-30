package com.web.ptitExam.service;

import com.web.ptitExam.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
   void registerUser (User user);
   boolean authenticate (String username, String password);
   User findByUsername (String username);
}
