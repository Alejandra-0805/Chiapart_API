val ktor_version = "3.0.3"
val exposed_version = "0.57.0"

plugins {
    application
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("io.ktor.plugin") version "3.0.3"
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.example.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    // --- Ktor Core & Servidor ---
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    // --- Ktor Content Negotiation (JSON) ---
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // --- Ktor Seguridad (JWT) ---
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")

    // --- Base de Datos (Exposed & Postgres) ---
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("com.zaxxer:HikariCP:6.2.1")

    // --- Utilidades e Integraciones ---
    implementation("io.github.cdimascio:dotenv-kotlin:6.5.0")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.cloudinary:cloudinary-http44:1.36.0")

    // --- Logs ---
    implementation("ch.qos.logback:logback-classic:1.5.16")

    // --- Testing ---
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}