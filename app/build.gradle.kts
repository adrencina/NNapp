plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("kotlin-kapt") // Plugin para KAPT (procesamiento de anotaciones)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.nnapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nnapp"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ROOM DEPENDENCIES:
    // Room Runtime: La biblioteca principal de Room.
    implementation(libs.androidx.room.runtime) // Asegúrate de que libs.room.runtime apunte a 2.6.1
    // Room KTX: Extensiones para usar coroutines y Flow con Room.
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.runtime.livedata) // Que apunte a 2.6.1 también.
    // Room Compiler: Procesador de anotaciones para Room.
    kapt("androidx.room:room-compiler:2.6.1")  // Actualizado a 2.6.1

    // MATERIAL3:
    implementation(libs.androidx.material3.v120)

    // GSON:
    implementation(libs.gson)

    // WEBKIT:
    implementation(libs.androidx.webkit)

    // COROUTINES:
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Material3 Design:
    implementation(libs.androidx.material3.v120)

    // Material Icons Extended:
    implementation(libs.androidx.material.icons.extended)

    // DATASTORE:
    implementation(libs.androidx.datastore.preferences)

    // FIREBASE BOM y dependencias:
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth.ktx)

    // DAGGER-HILT:
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hiltNavigationCompose)

    // ANDROIDX NAVIGATION:
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    // ANDROIDX CORE, LIFECYCLE, ACTIVITY COMPOSE:
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // JETPACK COMPOSE BOM y librerías de UI:
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // TESTING:
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}