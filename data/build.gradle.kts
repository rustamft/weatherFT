plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    namespace = "com.rustamft.data"
}

dependencies {
    implementation(project(":domain"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Constants.COROUTINE_VERSION}")
    // DataStore
    implementation("androidx.datastore:datastore:1.0.0")
    // Gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Hilt
    implementation("com.google.dagger:hilt-android:${Constants.HILT_VERSION}")
    kapt("com.google.dagger:hilt-compiler:${Constants.HILT_VERSION}")
}
