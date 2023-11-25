plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.githubapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.githubapp"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
//    kotlinOptions {
//        tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
//            kotlinOptions.jvmTarget = "1.8"
//        }
//    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            resources.excludes.add("/META-INF/LICENSE.md")
            resources.excludes.add("/META-INF/LICENSE-notice.md")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }

        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
}

dependencies {
    val lifecycle_version = "2.6.2"
    val rx = "2.1.0"
    val rxAndroid = "2.0.1"
    val rxRelay = "2.0.0"
    val rxBinding = "3.0.0"
    val work = "2.7.1"
    val hiltNavigationCompose = "1.0.0"
    val nav_version = "2.7.4"
    val androidxLifecycle = "2.6.1"
    val coroutines_version = "1.6.0"
    val turbine = "0.12.1"
    val mockkVersion = "1.13.8"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$androidxLifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("eu.bambooapps:compose-material3-pullrefresh:1.0.0")

    //hilt
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationCompose")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    //navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //work manager
    implementation("androidx.work:work-runtime-ktx:$work")

    //rxjava
    implementation("io.reactivex.rxjava2:rxandroid:$rxAndroid")
    implementation("io.reactivex.rxjava2:rxjava:$rx")
    implementation("com.jakewharton.rxrelay2:rxrelay:$rxRelay")
    implementation("com.jakewharton.rxbinding3:rxbinding:$rxBinding")

    //coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
    testImplementation ("org.jetbrains.kotlin:kotlin-test:1.7.0")
    testImplementation("app.cash.turbine:turbine:$turbine")
    testImplementation("io.mockk:mockk:$mockkVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
    androidTestImplementation("io.mockk:mockk:$mockkVersion")
    androidTestImplementation("io.mockk:mockk-android:$mockkVersion")
    androidTestImplementation("io.mockk:mockk-agent:$mockkVersion")
    androidTestImplementation("app.cash.turbine:turbine:$turbine")
//    androidTestImplementation("org.jetbrains.kotlin:kotlin-test:1.7.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}