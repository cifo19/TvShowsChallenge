import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
    }
}

dependencies {
    implementation(fileTree("lib").include("*.jar"))

    implementation(project(":domain"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.hilt)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGsonConverter)

    testImplementation(TestDependencies.mockk)
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.hamcrest)
    testImplementation(TestDependencies.archCoreTesting)
    testImplementation(TestDependencies.assertJ)
    testImplementation(TestDependencies.kotlinxCoroutinesTest)
    testImplementation(Dependencies.coroutinesCore)
}
