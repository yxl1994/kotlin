plugins {
    kotlin("multiplatform") version "{{kotlin_plugin_version}}"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

kotlin {
    ios()
}
