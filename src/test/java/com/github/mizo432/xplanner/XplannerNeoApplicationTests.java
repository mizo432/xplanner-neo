package com.github.mizo432.xplanner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class XplannerNeoApplicationTests {

	@Test
	void contextLoads() {
	}

}
