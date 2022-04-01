import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = Config.MIN_SDK_VERSION
        targetSdk = Config.TARGET_SDK_VERSION
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildTypes.forEach {
        it.buildConfigField("String", "API_KEY", "\"5d967c7c335764f39b1efbe9c5de9760\"")
        it.buildConfigField("String", "API_LANGUAGE_CODE", "\"en_US\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
        }
    }
}

dependencies {
    api(Dependencies.picasso) {
        because("Added via api since Application should know to draw dependency graph")
    }
    api(Dependencies.retrofit) {
        because("Added via api since Application should know to draw dependency graph")
    }
    api(Dependencies.retrofitGsonConverter) {
        because("Added via api since Application should know to draw dependency graph")
    }
    api(Dependencies.okHttpLogging) {
        because("Added via api since Application should know to draw dependency graph")
    }

    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)

    testImplementation(TestDependencies.mockk)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.hamcrest)
    testImplementation(TestDependencies.archCoreTesting)
    testImplementation(TestDependencies.assertJ)
    testImplementation(TestDependencies.okHttpMockWebServer)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)
    testImplementation(Dependencies.coroutinesCore)
}