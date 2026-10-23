package com.github.mizo432.xplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class XplannerNeoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XplannerNeoApplication.class, args);
	}

}
