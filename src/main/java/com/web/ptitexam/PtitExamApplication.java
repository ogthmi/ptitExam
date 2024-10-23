package com.web.ptitexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// Bật chế độ đa luồng cho việc gửi email
@EnableAsync
public class PtitExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtitExamApplication.class, args);
	}
}
