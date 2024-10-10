package com.web.ptitexam.service.implement;

import com.web.ptitexam.constant.Constant;
import com.web.ptitexam.dto.UserDTO;
import com.web.ptitexam.entity.Student;
import com.web.ptitexam.entity.Teacher;
import com.web.ptitexam.entity.User;
import com.web.ptitexam.repository.StudentRepository;
import com.web.ptitexam.repository.TeacherRepository;
import com.web.ptitexam.repository.UserRepository;
import com.web.ptitexam.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private HttpServletRequest request;

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
        if (userDTO.getRole().equals(Constant.ROLE_TEACHER)) {
            Teacher teacher = new Teacher();
            teacher.setUser(user); // Liên kết User với Teacher
            teacher.setTeacherId(userDTO.getTeacherId()); // Đặt Teacher ID
            teacher.setDepartment(userDTO.getDepartment()); // Đặt Department cho Teacher
            teacherRepository.save(teacher); // Lưu vào Teacher repository
        } else if (userDTO.getRole().equals(Constant.ROLE_STUDENT)) {
            Student student = new Student();
            student.setUser(user); // Liên kết User với Student
            student.setStudentId(userDTO.getStudentId()); // Đặt Student ID
            student.setMajor(userDTO.getMajor()); // Đặt Major cho Student
            student.setClassName(userDTO.getClassName()); // Đặt Class Name cho Student
            studentRepository.save(student); // Lưu vào Student repository
        }
    }

    @Override
    public void authenticateRegistration(UserDTO userDTO){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(),
                userDTO.getPassword(),
                List.of(new SimpleGrantedAuthority(userDTO.getRole()))
        );
        System.out.println(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

    @Override
    public boolean isUsernameTaken (String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public UserDTO getCurrentUser() {
        UserDTO userDTO = new UserDTO();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName());

        // Check if user is found before copying properties
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
        }
        return userDTO;
    }
}
