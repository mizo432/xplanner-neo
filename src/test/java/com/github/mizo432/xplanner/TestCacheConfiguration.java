package com.github.mizo432.xplanner;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

@TestConfiguration(proxyBeanMethods = false)
@EnableCaching
public class TestCacheConfiguration {

	@Bean
	public ConcurrentMapCacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
}
