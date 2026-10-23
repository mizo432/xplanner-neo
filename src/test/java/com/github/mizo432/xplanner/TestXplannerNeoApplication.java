package com.github.mizo432.xplanner;

import org.springframework.boot.SpringApplication;

public class TestXplannerNeoApplication {

	public static void main(String[] args) {
		SpringApplication.from(XplannerNeoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
