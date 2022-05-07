plugins {
    id("android-library.blueprint")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(Dependencies.picasso)
    implementation(Dependencies.recyclerView)

    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
    implementation(TestDependencies.espressoIdlingResource)

    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hilt)
}

kapt {
    correctErrorTypes = true
}
