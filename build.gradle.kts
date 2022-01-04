plugins {
    id("org.openjfx.javafxplugin") version "0.0.10"
    kotlin("jvm") version "1.4.32"
    application
}
group = "com.test"
version = "1.0-SNAPSHOT"

val kotlinVersion: String = "1.4.32"

repositories { mavenCentral() }

application { mainClassName = "com.example.MainKt" }

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.apache.poi:poi:5.0.0")
    implementation("org.apache.poi:poi-ooxml:5.0.0")
    implementation("org.apache.poi:poi-ooxml-full:5.0.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileTestKotlin { kotlinOptions.jvmTarget = "1.8" }
}

javafx {
    version = "11.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}