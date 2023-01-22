plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "${Constants.KOTLIN_VERSION}-1.0.6"
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.rustamft.weatherft"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "0.8.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Constants.COMPOSE_COMPILER_VERSION
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.rustamft.weatherft"
}

dependencies {

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation(project(":domain"))
    implementation(project(":data"))
    // Compose
    implementation("androidx.compose.ui:ui:${Constants.COMPOSE_VERSION}")
    implementation("androidx.compose.material:material:${Constants.COMPOSE_VERSION}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Constants.COMPOSE_VERSION}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Constants.COMPOSE_VERSION}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Constants.COMPOSE_VERSION}")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.25.1")
    // Compose destinations
    val composeDestinationsVersion = "1.7.15-beta"
    implementation("io.github.raamcosta.compose-destinations:core:$composeDestinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.43.2")
    kapt("com.google.dagger:hilt-compiler:2.43.2")
    // Hilt navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    // Coroutine test
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    // MockK
    testImplementation("io.mockk:mockk:1.12.5")
    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
}
