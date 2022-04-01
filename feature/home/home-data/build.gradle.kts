plugins {
    id("android-library.blueprint")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":common:util"))
    implementation(project(":feature:home:home-domain"))

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGsonConverter)

    // Room
    implementation(Dependencies.roomKtx)
    api(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)

    // Hilt
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
