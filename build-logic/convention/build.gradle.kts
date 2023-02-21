plugins {
    `kotlin-dsl`
}

group = "xyz.duckee.android.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.plugin.androidBuildTools)
    compileOnly(libs.plugin.kotlin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "duckee.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "duckee.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "duckee.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "duckee.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "duckee.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}
