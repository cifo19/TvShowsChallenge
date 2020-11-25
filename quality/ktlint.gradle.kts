val ktlint: Configuration by configurations.creating

dependencies {
  ktlint("com.pinterest:ktlint:0.39.0")
}

val outputDir = "${project.buildDir}/reports"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
  inputs.files(inputFiles)
  outputs.dir(outputDir)

  group = "verification"
  description = "Check Kotlin code style."
  classpath = ktlint
  main = "com.pinterest.ktlint.Main"
  args = listOf("src/**/*.kt", "--reporter=checkstyle,output=$outputDir/ktlint.xml")
}
