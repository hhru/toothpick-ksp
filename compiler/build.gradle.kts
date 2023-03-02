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

val fatJar = task("fatJar", type = Jar::class) {
//    baseName = "${project.name}-fat"
//    manifest {
//        attributes["Implementation-Title"] = "Gradle Jar File Example"
//        attributes["Implementation-Version"] = version
//        attributes["Main-Class"] = "com.mkyong.DateUtils"
//    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks.jar.get() as CopySpec)
}

//tasks.named("build") { dependsOn(fatJar) }

//tasks {
//    "build" {
//        dependsOn(fatJar)
//    }
//}

//kotlin {
//    jvm {
//        compilations {
//            val main = getByName("main")
//            tasks {
//                register<Jar>("fatJar") {
//                    group = "application"
//                    manifest {
//                        attributes["Implementation-Title"] = "Gradle Jar File Example"
//                        attributes["Implementation-Version"] = archiveVersion
//                        attributes["Main-Class"] = "[[mainClassPath]]"
//                    }
//                    archiveBaseName.set("${project.name}-fat")
//                    from(main.output.classesDirs, main.compileDependencyFiles)
//                    with(jar.get() as CopySpec)
//                }
//            }
//        }
//    }
//}
