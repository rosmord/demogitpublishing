
// Zip the project (but not the git)

tasks.register<Zip>("dozip") {
    from(".") {
        exclude("bin/**")
        exclude("build/**")
        exclude("out/**")
        exclude(".git/**")
        exclude(".gradle/**")
        exclude(".github/**")
        exclude(".vscode/**")
    }
    archiveFileName.set("${rootProject.name}-${project.version}.zip")
    destinationDirectory.set(layout.buildDirectory.dir("zip"))
    doLast {
        println("Zipped project to: ${archiveFile.get().asFile.absolutePath}")
    }
}
