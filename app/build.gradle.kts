plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "ru.tech.cookhelper"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.tech.cookhelper"
        minSdk = 21
        targetSdk = 33
        versionCode = 6
        versionName = "0.1.23-alpha"
        multiDexEnabled = true

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
                arg("room.incremental", "true")
                arg("room.expandProjection", "true")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += ("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    //App Compat
    implementation("androidx.appcompat:appcompat:1.7.0-alpha02")

    //Exif interface
    implementation("androidx.exifinterface:exifinterface:1.3.6")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-beta01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-beta01")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")

    //Compose
    implementation("androidx.activity:activity-compose:1.7.0-beta01")
    implementation("androidx.compose.ui:ui:1.4.0-beta01")
    implementation("androidx.compose.material3:material3:1.1.0-alpha06")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.0-alpha06")
    implementation("androidx.compose.material:material-icons-core:1.4.0-beta01")
    implementation("androidx.compose.material:material-icons-extended:1.4.0-beta01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-beta01")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha07")
    implementation("androidx.compose.foundation:foundation:1.4.0-beta01")
    implementation("androidx.compose.ui:ui-util:1.4.0-beta01")

    //Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-flowlayout:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-pager:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.29.1-alpha")

    //Coil
    implementation("io.coil-kt:coil:2.2.2")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-gif:2.2.2")
    implementation("io.coil-kt:coil-svg:2.2.2")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    //Room
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")

    //Navigation
    implementation("dev.olshevski.navigation:reimagined:1.3.1")
    implementation("dev.olshevski.navigation:reimagined-hilt:1.3.1")

    //SplashScreenApi
    implementation("androidx.core:core-splashscreen:1.0.0")

    //Result Retrofit Adapter
    implementation("com.github.skydoves:retrofit-adapters-result:1.0.5")

    //Compose Blur backport
    implementation("com.github.skydoves:cloudy:0.1.2")

    implementation(project(":dynamic_theme"))

}