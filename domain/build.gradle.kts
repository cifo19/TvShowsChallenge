plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(fileTree("libs").include("*.jar"))
    implementation(Dependencies.kotlin)
}