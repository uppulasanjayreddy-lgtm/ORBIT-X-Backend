package com.orbitx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class OrbitXApplication {
  public static void main(String[] args) { SpringApplication.run(OrbitXApplication.class, args); }
}
