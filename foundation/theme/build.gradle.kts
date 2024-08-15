plugins {
    apply(libs.plugins.android.library)
    apply(libs.plugins.kotlin.android)
    apply(libs.plugins.compose.core)
}

android {
    buildFeatures {
        namespace = "com.biped.locations.theme"
    }
}

dependencies {
    implementation(libs.lifecycle.runtime)
    implementation(libs.android.material)
    implementation(libs.compose.ui.googlefonts)
}