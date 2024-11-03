plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.sensorapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sensorapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    // Dodanie RecyclerView (upewnij się, że jest tylko jedna zależność do RecyclerView)
    implementation("androidx.recyclerview:recyclerview:1.3.2") // Użyj najnowszej wersji, jeśli dostępna

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
