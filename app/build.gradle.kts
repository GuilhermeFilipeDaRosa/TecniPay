plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.tecnicon.tecnipay"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tecnicon.tecnipay"
        minSdk = 22
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 27
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.faranjit:currency-edittext:1.0.1")
    implementation("com.cielo.lio:order-manager:1.8.6")
    //implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation(files("./dir/libposdigital-1.8.1-13-release.aar"))
}