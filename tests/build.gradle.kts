plugins {
    id(Plugins.Android.library)
    //id("com.android.test")
    id(Plugins.Kotlin.android)
    id(Plugins.Kotlin.kapt)
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)

        testInstrumentationRunner = "com.hotmart.test.CucumberRunner"
    }

    buildTypes {
        //targetProjectPath(":app")
    }

    variantFilter {
        if (buildType.name.contains("release") || buildType.name.contains("debug")) {
            ignore = true
        }
    }
}

dependencies {
    //localImplementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    localImplementation(Dependencies.Test.assertJ)
    localImplementation(Dependencies.Test.mockkAndroid)
    localImplementation(Dependencies.Test.mockServer)
    localImplementation(Dependencies.Test.robolectricAnnotations)
    localImplementation(Dependencies.Test.androidxCore)
    localImplementation(Dependencies.Test.androidxJunit)
    localImplementation(Dependencies.Test.fragment)
    localImplementation(Dependencies.Test.navigation)
    localImplementation(Dependencies.Test.espresso)
    localImplementation(Dependencies.Test.espressoContrib)
    localImplementation(Dependencies.Test.runner)
    localImplementation(Dependencies.Test.uiAutomator)

    localImplementation("io.cucumber:cucumber-android:4.8.4")
    localImplementation("io.cucumber:cucumber-picocontainer:4.8.1")
}