import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    application
}

group = "com.pet001kambala"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-lang:commons-lang:2.6")
    implementation("commons-logging:commons-logging:1.2")

    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}