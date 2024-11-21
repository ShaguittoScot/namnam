plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.amam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.amam"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.camera.lifecycle)  // CameraX lifecycle dependency
    implementation(libs.androidx.camera.view)  // CameraX view dependency
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.camera:camera-camera2:1.0.0")
    implementation("androidx.camera:camera-lifecycle:1.0.0")
    implementation("androidx.camera:camera-view:1.0.0")


    // Dependencia para trabajo con imágenes (por ejemplo, para procesamiento o carga de imágenes)
    implementation("com.squareup.picasso:picasso:2.71828")

    // Si vas a usar Google ML Kit (visión por computadora):
    implementation("com.google.mlkit:image-labeling:17.0.0")

    // O si prefieres usar TensorFlow Lite para modelos personalizados:
    implementation("org.tensorflow:tensorflow-lite:2.7.0")

    // Para trabajar con RecyclerView o listas de recetas
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Otras dependencias comunes
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
}