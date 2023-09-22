@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.sikarwartechservices"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sikarwartechservices"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_16
        targetCompatibility = JavaVersion.VERSION_16
    }

    viewBinding {
        enable = true
    }
}

dependencies {

//    implementation(libs.appcompat)
    implementation(libs.material)
//    implementation ("com.microsoft.sqlserver:mssql-jdbc:9.2.1.jre8")
    implementation (libs.jtds)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
//    implementation(libs.constraintlayout)
//    androidTestImplementation(libs.androidx.test.ext.junit)
//    androidTestImplementation(libs.espresso.core)

}