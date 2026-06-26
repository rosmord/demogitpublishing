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

// alternative approach for os detection
// The os of the current system (for jpackage)
// val os = System.getProperty("os.name").lowercase()


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
        } else if (org.gradle.internal.os.OperatingSystem.current().isLinux) {
            installerOptions.addAll(listOf(
                "--linux-shortcut"
            ))
        } else if (org.gradle.internal.os.OperatingSystem.current().isMacOsX) {
            println("Building for MacOS")
            installerType = "dmg"
            println("Installer type: $installerType")
            installerOptions.addAll(listOf(
                "--mac-package-identifier", "com.example.demo",
                "--mac-package-name", "DemoPublish",
                "--mac-dmg-content", "src/dist/documentation.md",
                "--mac-dmg-content", "src/dist/data",
            ))
        }
    }
}


// // Copy additional files into the installer packaging.

// if (org.gradle.internal.os.OperatingSystem.current().isWindows) {

//     val copyWinExtras by tasks.registering(Copy::class) {
//         dependsOn("jpackageImage")
//         from("src/dist")
//         into(layout.buildDirectory.dir("jpackage/DemoPublish"))
//     }

//     tasks.named("jpackage") {
//         dependsOn(copyWinExtras)
//     }
// }
