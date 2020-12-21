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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree("libs").include("*.jar"))

    implementation(project(":domain"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.retrofitGsonConverter)

    // Room
    implementation(Dependencies.roomKtx)
    api(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)

    // Hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltAndroidxCompiler)
}
