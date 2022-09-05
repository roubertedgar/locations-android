plugins {
    id(Plugins.Android.library)
    id(Dependencies.Hilt.plugin)
    id(Dependencies.Kotlin.kapt)
    id(Dependencies.Serialization.plugin)
}

android {
    buildTypes {
        local {
            buildConfigField("String", "BASE_URL", "\"http://127.0.0.1:8080/\"")
        }

        production {
            buildConfigField("String", "BASE_URL", "\"https://hotmart-mobile-app.herokuapp.com/\"")
        }
    }

    instrumentation {
        hasManagedDevice = true
    }
}

dependencies {
    implementation(Dependencies.Coroutines.android)

    implementation(Dependencies.Android.core)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.materialDesign)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.activity)
    implementation(Dependencies.Fragment.core)
    implementation(Dependencies.Navigator.fragment)
    implementation(Dependencies.Navigator.ui)
    implementation(Dependencies.Lifecycle.viewModel)

    implementation(Dependencies.coil)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.Serialization.core)

    implementation(Dependencies.Hilt.core)
    kapt(Dependencies.Hilt.compiler)

    implementation(project(":core"))
    implementation(project(":theme"))
    devImplementation(project(":test"))

    kaptTest(Dependencies.Hilt.compiler)
    kaptAndroidTest(Dependencies.Hilt.compiler)
}
