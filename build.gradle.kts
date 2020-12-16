plugins {
    kotlin("jvm") version "1.4.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.kotest:kotest-runner-junit5:4.3.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
