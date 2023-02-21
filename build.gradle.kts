buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.plugin.androidBuildTools)
        classpath(libs.plugin.kotlin)
    }
}

task("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
