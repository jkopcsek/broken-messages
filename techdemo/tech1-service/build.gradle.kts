
plugins {
    java
    idea
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

repositories {
    mavenCentral()
}

apply(plugin = "io.spring.dependency-management")

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val springBootVersion = "2.1.2"
val springCloudVersion = "Fishtown.RELEASE"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-stream-dependencies:$springCloudVersion")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    //implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
    implementation("io.nats:jnats:2.4.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    annotationProcessor("org.projectlombok:lombok:1.18.4")
    implementation("org.projectlombok:lombok:1.18.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("junit:junit")
}
