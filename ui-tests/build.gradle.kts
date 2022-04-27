plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    targetProjectPath(":app")
    testOptions.execution = "ANDROIDX_TEST_ORCHESTRATOR"
    compileSdk = Config.COMPILE_SDK_VERSION
    defaultConfig {
        minSdk = Config.MIN_SDK_VERSION
        targetSdk = Config.TARGET_SDK_VERSION

        testInstrumentationRunner = "com.scene.ui_tests.runner.HiltTestRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

configurations.all {
    resolutionStrategy {
        force("org.objenesis:objenesis:3.2")
    }
}

dependencies {
    implementation(project(":common:remote"))
    implementation(project(":common:base"))
    implementation(project(":common:util"))
    implementation(project(":feature:home:home-presentation"))
    implementation(project(":feature:home:home-data"))
    implementation(project(":feature:home:home-domain"))
    implementation(project(":feature:scene-detail"))
    implementation(project(":feature:scene-search"))

    implementation(Dependencies.appcompat)

    implementation("com.google.dagger:hilt-android-testing:2.41")
    kapt("com.google.dagger:hilt-compiler:2.41")

    implementation("androidx.test:runner:1.5.0-alpha02")
    implementation("androidx.test.ext:junit-ktx:1.1.3")

    androidTestUtil(TestDependencies.orchestrator)
    implementation(TestDependencies.mockk)
    implementation(TestDependencies.junit)
    implementation(TestDependencies.assertJ)
    implementation(TestDependencies.archCoreTesting)
    implementation("androidx.test:core-ktx:1.4.0")
    debugImplementation("androidx.fragment:fragment-testing:1.4.1")
}

kapt {
    correctErrorTypes = true
}