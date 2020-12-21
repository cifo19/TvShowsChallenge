import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Config.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Config.MIN_SDK_VERSION)
        targetSdkVersion(Config.TARGET_SDK_VERSION)
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
    implementation(fileTree("libs").include("*.jar"))

    api(project(":remote"))
    api(project(":cache"))
    implementation(project(":domain"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.hilt)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGsonConverter)
    implementation(Dependencies.okHttpLogging)

    testImplementation(TestDependencies.mockk)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.hamcrest)
    testImplementation(TestDependencies.archCoreTesting)
    testImplementation(TestDependencies.assertJ)
    testImplementation(TestDependencies.okHttpMockWebServer)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)
    testImplementation(Dependencies.coroutinesCore)

    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltAndroidxCompiler)
}