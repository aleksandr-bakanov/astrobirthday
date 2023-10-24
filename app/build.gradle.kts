plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("koin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
//    id("com.google.gms.google-services")
}

android {
    namespace = "bav.astrobirthday"

    compileSdk = 34

    defaultConfig {
        applicationId = "bav.astrobirthday"
        minSdk = 24
        targetSdk = 34
        versionCode = 25
        versionName = "2.2.0"

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "moshi.pro",
                "moshi-kotlin.pro",
                "kotlin-serialization.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.foundation:foundation:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

    // Koin for Kotlin
    implementation("io.insert-koin:koin-core:3.1.4")
    implementation("io.insert-koin:koin-core-ext:3.0.2")
    implementation("io.insert-koin:koin-android:3.1.4")
    implementation("io.insert-koin:koin-androidx-workmanager:3.1.4")
    implementation("io.insert-koin:koin-androidx-navigation:3.1.4")

    // Moshi
    implementation("com.squareup.moshi:moshi:1.13.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    // TODO: in navigation 2.4+ they've changed return to start destination of bottom nav view
    //   so it keeps backstack and don't glow bottom icon till we return to start destination
    //   This is against material design: https://material.io/components/bottom-navigation#behavior
    //   https://developer.android.com/jetpack/androidx/releases/navigation#version_240_3
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-paging:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.0")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:2.6.0")

    testImplementation("junit:junit:4.13.2")

    // Koin for Unit tests
    testImplementation("io.insert-koin:koin-test:3.1.4")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    // Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Custom tabs
    implementation("androidx.browser:browser:1.6.0")

    // Firebase
    implementation(project.dependencies.platform("com.google.firebase:firebase-bom:26.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation("io.github.l4digital:fastscroll:2.1.0")
    implementation("com.hannesdorfmann:adapterdelegates4:4.3.2")
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:4.3.2")

    // WorkManager
    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // This dependency is downloaded from the Google’s Maven repository.
    // So, make sure you also include that repository in your project's build.gradle file.
    implementation("com.google.android.play:core:1.10.3")

    // For Kotlin users also import the Kotlin extensions library for Play Core:
    implementation("com.google.android.play:core-ktx:1.8.1")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.3")

    // Compose Material Dialogs
    implementation("io.github.vanpra.compose-material-dialogs:core:0.9.0")
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.9.0")

    // KMM shared module
    implementation(project(":shared"))
}