plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.modmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.modmanager"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("com.squareup.duktape:duktape-android:1.3.0") // scripting (Duktape)
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
}
