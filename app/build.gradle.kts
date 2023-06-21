plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.authenticationwithviewmodel"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.authenticationwithviewmodel"
        minSdk = 25
        targetSdk = 33
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
}

dependencies {

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // Room
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.0")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    annotationProcessor("androidx.room:room-compiler:2.5.1")

    // Firebase
    implementation("com.google.firebase:firebase-bom:32.1.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.6.1")

    // Picasso
    implementation ("com.squareup.picasso:picasso:2.71828")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.github.bumptech.glide:compiler:4.12.0")

    // Map
    implementation("com.google.android.gms:play-services-maps:17.0.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-places:17.0.0")


    // Circled Photo
    implementation ("de.hdodenhof:circleimageview:3.1.0")


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
}