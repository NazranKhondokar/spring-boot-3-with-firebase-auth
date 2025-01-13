package com.nazran.springboot3firebseauth;

import com.nazran.springboot3firebseauth.utils.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot3WithFirebaseAuthApplication {
    public static void main(String[] args) {
        DotenvLoader.loadDotenv(); // Load .env file
        SpringApplication.run(SpringBoot3WithFirebaseAuthApplication.class, args);
    }
}
