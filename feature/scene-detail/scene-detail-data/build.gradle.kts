plugins {
    id("android-library.blueprint")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":common:util"))
    implementation(project(":feature:scene-detail:scene-detail-domain"))

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGsonConverter)

    // Room
    implementation(Dependencies.roomKtx)
    api(Dependencies.roomRuntime)

    // Hilt
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hilt)
}

kapt {
    correctErrorTypes = true
}
