import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.detekt

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
        classpath("com.google.gms:google-services:${Versions.GOOGLE_SERVICES}")
        classpath("com.google.firebase:firebase-appdistribution-gradle:${Versions.APP_DISTRIBUTION}")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version(Versions.DETEKT)
}

subprojects {
    tasks {
        withType<Detekt> { jvmTarget = "1.8" }
    }
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        parallel = true
        config = files("$rootDir/quality/detekt-config.yml")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
