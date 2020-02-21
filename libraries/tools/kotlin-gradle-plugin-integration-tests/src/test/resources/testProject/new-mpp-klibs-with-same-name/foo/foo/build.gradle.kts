plugins {
    kotlin("multiplatform")
}

group="sample.foo.foo"

kotlin {
    linuxX64("linux")
    js()

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-common"))
    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))
    }
}
