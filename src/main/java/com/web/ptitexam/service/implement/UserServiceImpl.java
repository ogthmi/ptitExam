package com.web.ptitexam.service.implement;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.repository.StudentRepository;
import com.web.ptitexam.repository.TeacherRepository;
import com.web.ptitexam.repository.UserRepository;
import com.web.ptitexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           StudentRepository studentRepository,
                           TeacherRepository teacherRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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

        if (userDTO.getRole().equals("ROLE_TEACHER")) {
            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacher.setTeacherId(userDTO.getTeacherId());
            teacher.setDepartment(userDTO.getDepartment());
            teacherRepository.save(teacher);
        } else if (userDTO.getRole().equals("ROLE_STUDENT")) {
            Student student = new Student();
            student.setUser(user);
            student.setStudentId(userDTO.getStudentId());
            student.setMajor(userDTO.getMajor());
            student.setClassName(userDTO.getClassName());
            studentRepository.save(student);
        }
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

    @Override
    public boolean isUsernameTaken (String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }
}
