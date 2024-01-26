plugins {
    id(Plugins.java_library)
    id(Plugins.kotlin_jvm)
    id(Plugins.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(Dependencies.Kotlin.stdlib)

    implementation(Dependencies.Coroutines.core)

    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.okHttp)

    implementation(Dependencies.Serialization.core)
    implementation(Dependencies.Serialization.converter)

    testImplementation(Dependencies.Test.jUnit)
}