plugins {
    id("duckee.android.feature")
    id("duckee.android.library.compose")
    id("duckee.android.hilt")
}

android {
    defaultConfig {
        namespace = "xyz.duckee.android.feature.collect"
    }
}
