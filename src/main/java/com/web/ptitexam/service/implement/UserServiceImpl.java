package com.web.ptitexam.service.implement;

import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.repository.StudentRepository;
import com.web.ptitexam.repository.TeacherRepository;
import com.web.ptitexam.repository.UserRepository;
import com.web.ptitexam.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        user.setUserId(UUID.randomUUID().toString()); // Tạo UUID cho user_id
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Mã hóa mật khẩu
        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setDob(userDTO.getDob());
        user.setGender(userDTO.getGender());
        user.setRole(userDTO.getRole());
        user.setEmail(userDTO.getEmail());

        // Lưu thông tin người dùng vào cơ sở dữ liệu
        userRepository.save(user);

        // Kiểm tra vai trò người dùng và lưu thông tin vào bảng tương ứng
        if (userDTO.getRole().equals("ROLE_TEACHER")) {
            Teacher teacher = new Teacher();
            teacher.setUser(user); // Liên kết User với Teacher
            teacher.setTeacherId(userDTO.getTeacherId()); // Đặt Teacher ID
            teacher.setDepartment(userDTO.getDepartment()); // Đặt Department cho Teacher
            teacherRepository.save(teacher); // Lưu vào Teacher repository
        } else if (userDTO.getRole().equals("ROLE_STUDENT")) {
            Student student = new Student();
            student.setUser(user); // Liên kết User với Student
            student.setStudentId(userDTO.getStudentId()); // Đặt Student ID
            student.setMajor(userDTO.getMajor()); // Đặt Major cho Student
            student.setClassName(userDTO.getClassName()); // Đặt Class Name cho Student
            studentRepository.save(student); // Lưu vào Student repository
        }
    }


    @Override
    public UserDTO authenticateAndGetUser(String username, String password) {
        // Tìm kiếm người dùng theo username
        User user = userRepository.findByUsername(username);

        // Kiểm tra sự tồn tại của người dùng và xác thực mật khẩu
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Map thông tin người dùng vào UserDTO
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }
        return null;
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public boolean isUsernameTaken (String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("\nAuthentication: " + authentication); // Logging

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            System.out.println("Username: " + username); // Logging

            User user = userRepository.findByUsername(username);
            if (user != null) {
                System.out.println("User found: " + user.getUsername()); // Logging
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(user, userDTO);
                return userDTO;
            } else {
                System.out.println("User not found in repository");
            }
        }
        return null;
    }



}
