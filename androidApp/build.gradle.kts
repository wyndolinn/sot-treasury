plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.wynndie.sottreasury"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.wynndie.sottreasury"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 3
        versionName = "1.0.0"
        versionNameSuffix = ""
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false

            applicationIdSuffix = ".debug"
            versionNameSuffix = "_debug"

            manifestPlaceholders["usesCleartextTraffic"] = true
            resValue("string", "app_name", "Treasury (debug)")
        }

        create("staging") {
            initWith(getByName("debug"))

            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            applicationIdSuffix = ".staging"
            versionNameSuffix = "_staging"

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            manifestPlaceholders["usesCleartextTraffic"] = true
            resValue("string", "app_name", "Treasury (staging)")
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            manifestPlaceholders["usesCleartextTraffic"] = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        resValues = true
        buildConfig = true
    }
}

dependencies {
    implementation(projects.sharedApp)

    implementation(libs.compose.uiToolingPreview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)

    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.components.resources)
    implementation(libs.compose.uiToolingPreview)
    implementation(libs.androidx.lifecycle.viewmodelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    debugImplementation(libs.compose.uiTooling)

    implementation(libs.kotlin.test)
}

base {
    val version = android.defaultConfig.versionName
    val suffix = android.defaultConfig.versionNameSuffix
    archivesName.set("sot-treasury-$version$suffix")
}
