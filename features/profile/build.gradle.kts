plugins {
    id(Plugins.library)
    id(Plugins.hilt)
    id(Plugins.kapt)
    id(Plugins.compose)
}

dependencies {
    implementation(Dependencies.Android.core)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.datastore)

    implementation(Dependencies.Lifecycle.runtime)

    implementation(Dependencies.Navigation.ui)
    implementation(Dependencies.Navigation.fragment)

    implementation(Dependencies.Hilt.core)
    kapt(Dependencies.Hilt.compiler)

    implementation(project(":features:settings"))

    implementation(project(":foundation:core"))
    implementation(project(":foundation:theme"))
    devImplementation(project(":foundation:test"))

    implementation(project(":coroutines:core"))
    testImplementation(project(":coroutines:test"))

    kaptTest(Dependencies.Hilt.compiler)
    kaptAndroidTest(Dependencies.Hilt.compiler)
}

