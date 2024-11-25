package com.web.ptitexam.service;

import java.util.List;

import com.web.ptitexam.dto.StudentDTO;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.User;

public interface UserService {
    void registerUser(UserDTO userDTO);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    void authenticateRegistration(UserDTO userDTO);

    UserDTO getCurrentUser();

    User findByUsername(String username);

    List<StudentDTO> searchStudents(String id);

    User getUserByEmail(String email);

    String generateToken();

    void updateUserPassword(User user, String password);
}
