plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"

}

android {
    namespace = "com.danielpons.aplicacionviajes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.danielpons.aplicacionviajes"
        minSdk = 26
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.runtime.saved.instance.state)
    implementation(libs.junit.junit)
    implementation(libs.core.ktx)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.play.services.maps)
    implementation(libs.places)
    implementation(libs.androidx.core.i18n)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(platform("io.github.jan-tennert.supabase:bom:3.1.3"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:storage-kt:1.0.0")
    implementation("io.ktor:ktor-client-android:3.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0") // Añadir esta línea
    implementation("com.google.accompanist:accompanist-permissions:0.37.2")
    implementation ("io.coil-kt:coil-compose:2.4.0")

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit.v113)

    testImplementation("io.mockk:mockk:1.13.5")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("com.google.maps.android:maps-compose:2.11.0")
    implementation("com.google.accompanist:accompanist-pager:0.34.0")
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.30.1")
}