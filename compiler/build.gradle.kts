import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    `maven-publish`
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.ksp)
    implementation(projects.compilerFactory)
    implementation(projects.compilerMemberinjector)

    testImplementation(libs.junit4)
    testImplementation(libs.compiletesting.kt)
    testImplementation(projects.compilerTest)
}

tasks.register("fatJar", type = Jar::class) {
    val directories = configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }

    from(directories)
    duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.INCLUDE
    with(tasks.jar.get() as CopySpec)
}
