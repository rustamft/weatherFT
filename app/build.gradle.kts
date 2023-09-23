plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "${Constants.KOTLIN_VERSION}-1.0.13"
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
    compileSdk = 34
    defaultConfig {
        applicationId = "com.rustamft.weatherft"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.8.9"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Constants.COMPOSE_COMPILER_VERSION
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.rustamft.weatherft"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Compose
    implementation("androidx.compose.ui:ui:${Constants.COMPOSE_VERSION}")
    implementation("androidx.compose.material:material:${Constants.COMPOSE_VERSION}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Constants.COMPOSE_VERSION}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Constants.COMPOSE_VERSION}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Constants.COMPOSE_VERSION}")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    // Compose destinations
    val composeDestinationsVersion = "1.9.53"
    implementation("io.github.raamcosta.compose-destinations:core:$composeDestinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    // Hilt
    val hiltVersion = "2.48"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    // Hilt navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    // Coroutine test
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // MockK
    testImplementation("io.mockk:mockk:1.13.8")
    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}
