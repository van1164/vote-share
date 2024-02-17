import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
	kotlin("plugin.jpa") version "1.9.21"
	kotlin("kapt") version "1.9.22"
}

group = "com.van1164"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web"){
		exclude("commons-logging","commons-logging")
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation ("com.mysql:mysql-connector-j:8.3.0")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    testImplementation("org.testng:testng:7.1.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	compileOnly ("org.projectlombok:lombok")

	//swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("io.swagger.core.v3:swagger-annotations:2.2.16")
	//implementation("io.springfox:springfox-boot-starter:3.0.0")

	// JSON in MySQL
	implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.7.0")

	implementation ("org.springframework.boot:spring-boot-starter-data-redis")

	//S3
	implementation("com.amazonaws:aws-java-sdk-s3:1.12.268")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.0-RC2")

	// json 직렬화
	implementation ("com.squareup.retrofit2:converter-gson:2.7.1")


	//queryDsl
	implementation("com.querydsl:querydsl-jpa:5.0.0")
	implementation("com.querydsl:querydsl-apt:5.0.0")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("javax.persistence:javax.persistence-api:2.2")
	annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")


	//Spring Batch
	testImplementation("org.springframework.batch:spring-batch-test")
	implementation("org.springframework.boot:spring-boot-starter-batch")

	//logger
	implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")
	//implementation("org.slf4j:slf4j-api:1.7.30")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	enabled = false
	useJUnitPlatform()
}
