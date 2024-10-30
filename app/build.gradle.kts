plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  alias(libs.plugins.hilt)
  id("kotlin-kapt")
}

android {
  namespace = "co.nz.tsb.interview.bankrecmatchmaker"
  compileSdk = 34
  
  defaultConfig {
    applicationId = "co.nz.tsb.interview.bankrecmatchmaker"
    minSdk = 21
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    
    testInstrumentationRunner = "co.nz.tsb.interview.bankrecmatchmaker.CustomTestRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
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
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.coroutines)
  implementation(libs.androidx.lifecycle.viewmodel)
  implementation(libs.androidx.activity.ktx)
  implementation(libs.hilt)
  implementation(libs.core)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.ui.graphics)
  kapt(libs.hiltCompiler)
  implementation (libs.androidx.hilt.navigation.compose)
  val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
  implementation(composeBom)
  androidTestImplementation(composeBom)
  implementation(libs.material3)
  implementation(libs.ui.tooling.preview)
  debugImplementation(libs.ui.tooling)
  androidTestImplementation(libs.ui.test.junit4)
  debugImplementation(libs.ui.test.manifest)
  implementation(libs.androidx.material.icons.core)
  implementation(libs.androidx.material.icons.extended)
  testImplementation(libs.junit)
  testImplementation(libs.coroutines.test)
  testImplementation(libs.mockk)
  testImplementation(libs.core.testing)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.espresso.contrib)
  androidTestImplementation(libs.androidx.espresso.intents)
  androidTestImplementation(libs.hilt.testing)
  kaptAndroidTest(libs.hiltCompiler)
}