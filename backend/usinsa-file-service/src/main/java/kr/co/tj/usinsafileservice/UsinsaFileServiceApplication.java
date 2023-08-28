package kr.co.tj.usinsafileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UsinsaFileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsinsaFileServiceApplication.class, args);
	}

}
