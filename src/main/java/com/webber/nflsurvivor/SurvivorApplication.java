package com.webber.nflsurvivor;

import com.webber.nflsurvivor.configuration.DateTimeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(basePackages = "com.webber.nflsurvivor")
@EnableScheduling
@EnableConfigurationProperties(DateTimeProperties.class)
public class SurvivorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurvivorApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("pw"));
	}

}
