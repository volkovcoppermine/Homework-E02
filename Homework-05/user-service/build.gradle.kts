plugins {
    id("java")
}

group = "org.volkov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:4.1.0-M4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:4.1.0-M4")
    implementation("org.springframework.kafka:spring-kafka:4.0.5")
    runtimeOnly("org.postgresql:postgresql:42.7.10")
    implementation("org.projectlombok:lombok:1.18.44")
    annotationProcessor("org.projectlombok:lombok:1.18.44")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}