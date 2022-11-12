import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
    id("maven-publish")
}

group = "com.tosmo"
version = "0.1.0"

publishing {
    publications {
        create("maven_public", MavenPublication::class) {
//            artifactId = "koffice"
            from(components.getByName("kotlin"))
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}