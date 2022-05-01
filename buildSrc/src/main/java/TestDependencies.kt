object TestDependencies {
    const val junit = "junit:junit:${Versions.JUNIT}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.ARCH_CORE_TESTING}"
    const val assertJ = "org.assertj:assertj-core:${Versions.ASSERTJ}"
    const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.HAMCREST}"
    const val mockk = "io.mockk:mockk:${Versions.MOCKK}"
    const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES}"
    const val okHttpMockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.OK_HTTP}"
    const val orchestrator = "androidx.test:orchestrator:${Versions.ORCHESTRATOR}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
    const val hiltTest = "com.google.dagger:hilt-android-testing:${Versions.HILT}"
    const val androidXJUnit = "androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT}"

    // Contains activityScenarioRule<> that starts activity
    const val junitKtx = "androidx.test.ext:junit-ktx:${Versions.ANDROIDX_JUNIT}"

    // Contains AndroidJUnitRunner
    const val testRunner = "androidx.test:runner:${Versions.ANDROIDX_TEST}"

    // "Contains launchActivity<>"
    const val testCoreKtx = "androidx.test:core-ktx:${Versions.ANDROIDX_TEST}"
}
