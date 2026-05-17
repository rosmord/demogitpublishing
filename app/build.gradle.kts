plugins {
    application
    id("org.beryx.runtime") version "2.0.1"
}

version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "demo.Main"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

runtime {
    options.set(listOf("--strip-debug", "--no-header-files", "--no-man-pages"))
    modules.set(listOf("java.desktop", "java.logging"))

    jpackage {
        appVersion = project.version.toString()
        imageName = "DemoPublish"
        installerName = "DemoPublish"

        val type = project.findProperty("installerType") as String?
        if (type != null) {
            installerType = type
        }

        if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
            installerOptions.addAll(listOf(
                "--win-per-user-install",
                "--win-dir-chooser",
                "--win-menu",
                "--win-shortcut"
            ))
        }
    }
}
