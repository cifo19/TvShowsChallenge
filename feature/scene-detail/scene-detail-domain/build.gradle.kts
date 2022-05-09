plugins {
    id("android-library.blueprint")
}

dependencies {
    implementation(project(":common:util"))

    implementation(Dependencies.hilt)
}
