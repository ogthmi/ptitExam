//package com.web.ptitexam.security;
//
//import com.web.ptitexam.constant.Constant;
//import com.web.ptitexam.entity.User;
//import com.web.ptitexam.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.UUID;
// TẠO TÀI KHOẢN ADMIN
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Kiểm tra xem admin đã tồn tại chưa
//        if (userRepository.findByUsername("admin01") == null) {
//            // Tạo tài khoản admin mới
//            User adminUser = new User();
//            adminUser.setUserId(UUID.randomUUID().toString());
//            adminUser.setUsername("admin01");
//            adminUser.setPassword(passwordEncoder.encode("admin123"));
//            adminUser.setLastname("Admin");
//            adminUser.setFirstname("Số 01");
//            adminUser.setDob(LocalDate.parse("1990-01-01"));
//            adminUser.setGender(Constant.GENDER_FEMALE);
//            adminUser.setRole(Constant.ROLE_ADMIN);
//            adminUser.setEmail("admin01@ptit.edu.vn");
//
//            userRepository.save(adminUser);
//            System.out.println("Admin user created: admin/admin123");
//        }
//    }
//}