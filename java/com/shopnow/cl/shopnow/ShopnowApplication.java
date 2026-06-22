package com.shopnow.cl.shopnow;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopnow.cl.shopnow.model.Role;
import com.shopnow.cl.shopnow.model.User;
import com.shopnow.cl.shopnow.repository.UserRepository;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients

public class ShopnowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopnowApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode("1234"));
                user.setRole(Role.ROLE_ADMIN);
                repo.save(user);
            }
        };
    }
}