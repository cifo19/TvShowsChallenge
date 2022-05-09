plugins {
    id("android-library.blueprint")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":common:util"))
    implementation(project(":common:base"))
    implementation(project(":feature:scene-detail:scene-detail-domain"))

    implementation(Dependencies.constraintLayout)

    // ktx
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleViewModelKtx)

    // Dependency Injection
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hilt)

    testImplementation(TestDependencies.mockk)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.hamcrest)
    testImplementation(TestDependencies.archCoreTesting)
    testImplementation(TestDependencies.assertJ)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)
    testImplementation(TestDependencies.okHttpMockWebServer)
    testImplementation(Dependencies.coroutinesCore)
}

kapt {
    correctErrorTypes = true
}
