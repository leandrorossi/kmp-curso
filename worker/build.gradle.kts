import com.varabyte.kobweb.gradle.worker.util.configAsKobwebWorker
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kobweb.worker)
}

group = "org.example.blogmultiplatform.worker"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebWorker()

    sourceSets {
        jsMain.dependencies {
            implementation(libs.kobweb.worker)
        }
    }
}
