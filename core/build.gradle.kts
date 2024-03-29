plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)

        versionCode = 1
        versionName = "1.0"

        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        all {
            isMinifyEnabled = false
        }
    }

    flavorDimensions("environment")
    productFlavors {
        create("develop") {}
        create("production") {}
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.fragment:fragment:1.2.0-rc02")
    implementation("androidx.fragment:fragment-ktx:1.2.0-rc02")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.1.0")
    implementation("com.google.firebase:firebase-analytics:17.2.1")

    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.60-eap-25")
    api("com.google.android.material:material:1.2.0-alpha01")
    api("androidx.constraintlayout:constraintlayout:1.1.3")
    api("androidx.core:core-ktx:1.1.0")
    api("androidx.navigation:navigation-fragment-ktx:2.2.0-rc02")
    api("androidx.navigation:navigation-ui-ktx:2.2.0-rc02")
    api("org.koin:koin-android:2.0.1")
    api("org.koin:koin-androidx-viewmodel:2.0.1")
    api("com.squareup.moshi:moshi:1.9.1")
    api("com.jakewharton.timber:timber:4.7.1")
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
    api("com.crashlytics.sdk.android:crashlytics:2.10.1")
}