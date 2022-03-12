plugins {
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
}

dependencies {
	api(project(":cubetiq-utils"))
	api(project(":cubetiq-data-jpa"))

	implementation("io.jsonwebtoken:jjwt:0.9.1")
	compileOnly("javax.servlet:javax.servlet-api:4.0.1")

	implementation("org.springframework.security:spring-security-core")
	implementation("org.springframework.security:spring-security-config")
	implementation("org.springframework.security:spring-security-web")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}