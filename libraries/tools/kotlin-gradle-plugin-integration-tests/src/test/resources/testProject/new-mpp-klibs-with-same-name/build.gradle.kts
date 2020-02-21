plugins {
    kotlin("multiplatform").version("<pluginMarkerVersion>")
}

group = "sample"

allprojects {
    repositories {
        mavenLocal()
        jcenter()
    }
}

kotlin {
    linuxX64("linux")
    js()

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-common"))
        implementation(project(":foo"))
    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))
    }
}
