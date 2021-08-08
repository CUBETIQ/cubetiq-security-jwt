plugins {
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation("org.springframework.security:spring-security-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}