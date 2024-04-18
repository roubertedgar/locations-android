plugins {
    apply(libs.plugins.android.library)
    apply(libs.plugins.kotlin.android)
    apply(libs.plugins.ksp)
    apply(libs.plugins.hilt)
}

android {
    namespace = "biped.works.database"

    defaultConfig {
        javaCompileOptions.annotationProcessorOptions {
            arguments += mapOf(
                "room.schemaLocation" to "$projectDir/schemas",
                "room.incremental" to "true",
                "room.expandProjection" to "true"
            )
        }
    }
}

dependencies {
    api(libs.room.runtime)
    implementation(Dependencies.Room.extesions)
    ksp(Dependencies.Room.compiler)

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}