package com.emre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //Auth -> User olduğu için şimdilik sadece buraya ekledik.
// Yani auth user a bilgi göndereceği için. İhtiyaç olursa ona da eklenir.
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class);
    }
}