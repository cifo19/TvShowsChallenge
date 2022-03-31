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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
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
