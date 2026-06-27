plugins {
    application
    id("org.beryx.runtime") version "2.0.1"
}

version = "1.1.0"


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
        imageName = "DemoPublish-${project.version}"
        installerName = "DemoPublish"

        val type = project.findProperty("installerType") as String?
        if (type != null) {
            installerType = type
        }

        if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
            // Default installer type is msi
            if (installerType == null) {
                installerType = "msi"
            }
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
            // On MacOS, our default installer type is dmg
            if (installerType == null) {
                installerType = "dmg"
            }
            
            installerOptions.addAll(listOf(
                "--mac-package-identifier", "com.example.demo",
                "--mac-package-name", "DemoPublish"                
            ))

            if (installerType == "dmg") {
                installerOptions.addAll(listOf(
                    "--mac-dmg-content", "${projectDir}/src/dist/documentation.md",
                    "--mac-dmg-content", "${projectDir}/src/dist/data"
                ))
            }
        }
    }
}


// Copy extra files for inclusion into the Windows MSI installer
if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
    val copyWinExtras by tasks.registering(Copy::class) {
        dependsOn("jpackageImage")
        from("src/dist")
        into(layout.buildDirectory.dir("jpackage/DemoPublish-${project.version}"))
    }
    tasks.named("jpackage") {
        dependsOn(copyWinExtras)
    }
}