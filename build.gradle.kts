plugins {
	java
	id("org.springframework.boot") version "4.1.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
	id("com.github.spotbugs") version "6.5.12"
}

group = "com.github.mizo432"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot starters
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.0")

	// Bootstrap 5 via WebJars
	implementation("org.webjars:bootstrap:5.3.8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("org.postgresql:postgresql")

	// xplanner-plus dependencies
	// Apache Commons
	implementation("org.apache.commons:commons-lang3:3.17.0")
	implementation("commons-beanutils:commons-beanutils-core:1.8.3")


	// Logging - Logback (Spring Boot default)
	implementation("org.slf4j:jcl-over-slf4j:2.0.20")

	// Database
	implementation("org.eclipse.angus:angus-mail:2.0.3")

	// Servlet API (provided by container)
	compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.48")
	annotationProcessor("org.projectlombok:lombok:1.18.48")
	testCompileOnly("org.projectlombok:lombok:1.18.48")
	testAnnotationProcessor("org.projectlombok:lombok:1.18.48")

	// SpotBugs annotations
	compileOnly("com.github.spotbugs:spotbugs-annotations:4.10.2")

	// Test dependencies
 testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:testcontainers-junit-jupiter")
	testImplementation("org.testcontainers:testcontainers-postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

tasks.withType<ProcessResources> {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


// Source sets for xplanner-plus structure
sourceSets {
	main {
		java {
			srcDirs("src/main/java")
		}
		resources {
			srcDirs("src/main/resources")
		}
	}
	test {
		java {
			srcDirs("src/test/java")
		}
		resources {
			srcDirs("src/test/resources")
		}
	}
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	options.compilerArgs.add("-Xlint:deprecation")
}

// SpotBugs configuration
spotbugs {
	toolVersion.set("4.10.2")
	ignoreFailures.set(false)
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
	reports.create("xml") {
		required.set(true)
	}
	reports.create("html") {
		required.set(true)
	}
	// Suppress US_USELESS_SUPPRESSION_ON_CLASS warnings
	// These occur when @SuppressFBWarnings is applied to classes where the actual bug pattern
	// is in generated code (Lombok) or inherited methods, making the suppression technically "useless"
	// but still necessary for the actual bugs
	excludeFilter.set(file("spotbugs-exclude.xml"))
}

// Node.js and npm configuration (removed - no longer needed for Materialize CSS)
