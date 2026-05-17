# Demopublish

A very basic Swing application, to test the use of jpackage and the publishing system of github.

The idea is to generate Mac and Windows packages using github for cross-compilation.

## Explanation

The application itself is a very basic Swing app. Nothing really interesting there. The main class is `demo.Main`.


### Configuration

The main point is that we use the `org.beryx.runtime` to generate the runtime code. I had to choose version **2.0.1**, because older versions would not work with java 25 and my recent gradle installation.

So, on your local computer, running `./gradlew package` will generate the ready-to-use package for your platform, at least on the mac.

To use it for cross-plaform compilation, we use *github actions.* They are configured in `.github/workflows/publish.yml`.


