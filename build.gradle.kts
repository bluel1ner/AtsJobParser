plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.blueliner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.velopayments:java-spring-resttemplate:2.32.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}