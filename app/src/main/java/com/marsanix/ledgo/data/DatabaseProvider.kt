package com.marsanix.ledgo.data

import android.content.Context
import android.util.Log

// Kelas Helper untuk Dependency Injection Sederhana
class DatabaseProvider(private val context: Context) {

    private val database: DigitalEconomyDatabase by lazy {
        DigitalEconomyDatabase.getInstance(context)
    }

    val topicDao: TopicDao by lazy {
        database.topicDao()
    }

    val userProgressDao: UserProgressDao by lazy {
        database.userProgressDao()
    }

    val settingsDao: AppSettingsDao by lazy {
        database.settingsDao()
    }
}