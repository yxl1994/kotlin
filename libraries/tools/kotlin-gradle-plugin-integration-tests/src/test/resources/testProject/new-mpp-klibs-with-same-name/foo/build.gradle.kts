plugins {
    kotlin("multiplatform")
}

group="sample.foo"

kotlin {
    linuxX64("linux")
    js()

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-common"))
        implementation(project(":foo:foo"))
    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))
    }
}


