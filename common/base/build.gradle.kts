plugins {
    id("android-library.blueprint")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

dependencies {

    // this dependency should be broken
    implementation(project(":common:remote")) {
        because("BaseActivity knows NoConnectionException")
    }

    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.materialDialogs)

    // ktx
    implementation(Dependencies.coreKtx)

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
