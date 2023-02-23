/*
 * Copyright 2023 The Duckee Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    implementation(libs.coil.compose)
    implementation(libs.accompanist.placeholder)
}
