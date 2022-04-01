import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("dagger.hilt.android.plugin")
}

apply(from = "../quality/ktlint.gradle.kts")

android {
    compileSdk = Config.COMPILE_SDK_VERSION
    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdk = Config.MIN_SDK_VERSION
        targetSdk = Config.TARGET_SDK_VERSION
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        named("debug").configure {
            storeFile = file("../debug.keystore")
        }
    }
    buildTypes {
        named("debug").configure {
            firebaseAppDistribution {
                releaseNotesFile = "${project.rootDir}/release_notes.txt"
                testers = "caferkaya1989@gmail.com"
            }
        }
        named("release").configure {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildTypes.forEach {
        it.buildConfigField("String", "API_KEY", "\"5d967c7c335764f39b1efbe9c5de9760\"")
        it.buildConfigField("String", "API_LANGUAGE_CODE", "\"en_US\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        dataBinding = true
    }
    lint {
        abortOnError = true
        absolutePaths = false
        baseline = file("rootDir/app/lint-baseline.xml")
        disable += setOf("Overdraw", "LockedOrientationActivity")
        enable += setOf("SyntheticAccessor")
        warningsAsErrors = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
        }
    }
}

androidExtensions {
    isExperimental = true
}

dependencies {
    implementation(project(":common:remote"))
    implementation(project(":feature:home:home-presentation"))
    implementation(project(":feature:home:home-data"))
    implementation(project(":feature:home:home-domain"))
    implementation(project(":feature:scene-detail"))
    implementation(project(":feature:scene-search"))

    // Dependency Injection
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hilt)

    // Firebase
    implementation(Dependencies.firebase)
}

kapt {
    correctErrorTypes = true
}
