plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.COMPILE_SDK_VERSION
    targetProjectPath(":app")
    testOptions.execution = "ANDROIDX_TEST_ORCHESTRATOR"
    defaultConfig {
        minSdk = Config.MIN_SDK_VERSION
        targetSdk = Config.TARGET_SDK_VERSION

        testInstrumentationRunner = "com.scene.uitest.runner.HiltTestRunner"
        // The following argument makes the Android Test Orchestrator run its
        // "pm clear" command after each test invocation. This command ensures
        // that the app's state is completely cleared between tests.
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
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
    implementation(project(":app"))
    implementation(project(":feature:home:home-presentation"))
    implementation(project(":feature:home:home-domain"))
    implementation(project(":feature:home:home-data"))
    implementation(project(":common:base"))
    implementation(project(":common:remote"))

    kapt(Dependencies.hiltCompiler)
    implementation(TestDependencies.hiltTest)
    implementation(TestDependencies.testRunner)
    implementation(TestDependencies.androidXJUnit)
    implementation(TestDependencies.junitKtx)
    implementation(TestDependencies.testCoreKtx)
    implementation(TestDependencies.mockk)
    implementation(TestDependencies.junit)
    implementation(TestDependencies.assertJ)
    implementation(TestDependencies.espressoCore)
    implementation(TestDependencies.espressoContrib) {
        because("Without this library it does not start application")
    }
    androidTestUtil(TestDependencies.orchestrator)
}

kapt {
    correctErrorTypes = true
}
