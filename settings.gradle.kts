@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "duckee-android"
include(":app")
include(":core:designsystem")
include(":core:navigation")
include(":core:ui")
include(":core:model")
include(":core:network:api")
include(":core:network:impl")
include(":core:data:api")
include(":core:data:impl")
include(":core:domain")
include(":feature:explore")
include(":feature:signin")
