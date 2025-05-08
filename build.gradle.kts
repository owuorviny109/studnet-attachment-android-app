plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false // only once!

    // Dagger Hilt Plugin - Apply false to avoid it being applied at the root level
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

buildscript {
    dependencies {
        // Hilt Gradle plugin for applying to the module
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
}

