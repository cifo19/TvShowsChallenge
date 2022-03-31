import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
}

apply(from = "../quality/ktlint.gradle.kts")

android {
    compileSdkVersion(Config.COMPILE_SDK_VERSION)
    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdkVersion(Config.MIN_SDK_VERSION)
        targetSdkVersion(Config.TARGET_SDK_VERSION)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependencies.picasso)
    implementation(Dependencies.kotlin)
    implementation(Dependencies.activity)
    implementation(Dependencies.fragment)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.cardView)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.material)
    implementation(Dependencies.materialDialogs)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    // ktx
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleViewModelKtx)

    // ViewModel and LiveData
    implementation(Dependencies.lifecycleExtensions)

    // Networking
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpUrlConnection)
    implementation(Dependencies.okHttpLogging)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGsonConverter)

    // Dependency Injection
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltAndroidxCompiler)
    implementation(Dependencies.hilt)
    implementation(Dependencies.hiltViewModel)

    // Firebase
    implementation(Dependencies.firebase)

    testImplementation(TestDependencies.mockk)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.hamcrest)
    testImplementation(TestDependencies.archCoreTesting)
    testImplementation(TestDependencies.assertJ)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)
    testImplementation(TestDependencies.okHttpMockWebServer)
    testImplementation(Dependencies.coroutinesCore)
}
