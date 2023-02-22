plugins {
    id("duckee.android.library")
    id("duckee.android.library.compose")
}

android {
    defaultConfig {
        namespace = "xyz.duckee.android.core.designsystem"
    }
}

dependencies {
    implementation(libs.material2.android)
    implementation(libs.androidx.lifecycle)
}
