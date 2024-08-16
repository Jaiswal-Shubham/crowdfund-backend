package com.jaisshu.crowdfund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = { "com.jaisshu.crowdfund"})
@EnableJpaRepositories(basePackages = "com.jaisshu.crowdfund.repository")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CrowdfundApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdfundApplication.class, args);
	}

}
