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
  kapt(libs.hiltCompiler)

  val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
  implementation(composeBom)
  androidTestImplementation(composeBom)

  // Choose one of the following:
  // Material Design 3
  implementation("androidx.compose.material3:material3")
  // or Material Design 2
  implementation("androidx.compose.material:material")
  // or skip Material Design and build directly on top of foundational components
  implementation("androidx.compose.foundation:foundation")
  // or only import the main APIs for the underlying toolkit systems,
  // such as input and measurement/layout
  implementation("androidx.compose.ui:ui")

  // Android Studio Preview support
  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")

  // UI Tests
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

  // Optional - Included automatically by material, only add when you need
  // the icons but not the material library (e.g. when using Material3 or a
  // custom design system based on Foundation)
  implementation("androidx.compose.material:material-icons-core")
  // Optional - Add full set of material icons
  implementation("androidx.compose.material:material-icons-extended")
  // Optional - Add window size utils
  implementation("androidx.compose.material3.adaptive:adaptive")

  // Optional - Integration with activities
  implementation("androidx.activity:activity-compose:1.9.2")
  // Optional - Integration with ViewModels
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
  // Optional - Integration with LiveData
  implementation("androidx.compose.runtime:runtime-livedata")
  
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