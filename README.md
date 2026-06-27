# Demopublish

A very basic Swing application, to test the use of jpackage and the publishing system of github.

The idea is to generate Mac and Windows packages using github for cross-compilation.

## Explanation

The application itself is a very basic Swing app. Nothing really interesting there. The main class is `demo.Main`.

The whole interest of the project is its use of `jpackage` and `github` to produce platform-specific distributions automatically.


### Configuration

The main point is that we use the plugin [org.beryx.runtime](https://badass-runtime-plugin.beryx.org/releases/latest/) to generate the runtime code. I had to choose version **2.0.1**, because older versions would not work with java 25 and my recent gradle installation.

Reading the documentation of this plugin, **and** of [jpackage](https://docs.oracle.com/en/java/javase/25/docs/specs/man/jpackage.html) is important if you want to use them.

So, on your local computer, running `./gradlew package` will generate the ready-to-use package for your platform, at least on the mac.

### Cross-compilation

To use it for cross-plaform compilation, we use *github actions.* They are configured in `.github/workflows/publish.yml`.

The file is activated when we push a tag whose name starts with `release-` (e.g. `release-1.0`).

~~~yml
on:
  push:
    tags:
      - 'release-*'
~~~

It contains a job for building on mac and a job for building on windows. 

A job contains either direct code (such as `./gradlew jpackage`) or calls to *actions*, which are defined in [.github/actions](https://github.com/actions).


Note that jobs need to get the code, get the correct java version, build the package, and then updload it in the correct format.

Running

~~~bash
git tag release-1.0
git push origin release-1.0
~~~

Will trigger the creation of the packages. They can be found in the *release* part of the repository (not package).

Four distributions are generated:

- mac for “Apple silicon” (a.k.a. M1)
- mac for Intel processors (older macs)
- windows with installer (msi)
- windows executable (exe)

 
