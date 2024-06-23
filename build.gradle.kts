buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Constants.HILT_AGP_VERSION}")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.5.0" apply false
    id("com.android.library") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version Constants.KOTLIN_VERSION apply false
    id("org.jetbrains.kotlin.jvm") version Constants.KOTLIN_VERSION apply false
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}
