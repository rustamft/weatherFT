plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "com.rustamft.data"
}

dependencies {

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation(project(":domain"))
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
