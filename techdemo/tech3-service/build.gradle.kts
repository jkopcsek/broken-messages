
plugins {
    java
    idea
    id("org.springframework.boot")
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.nats:jnats:2.4.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("junit:junit")
}
