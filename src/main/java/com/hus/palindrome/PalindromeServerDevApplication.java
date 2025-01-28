package com.hus.palindrome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan
public class PalindromeServerDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalindromeServerDevApplication.class, args);
	}

}
