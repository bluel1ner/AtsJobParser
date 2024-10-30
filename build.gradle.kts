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
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}