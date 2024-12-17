package com.marsanix.ledgo.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marsanix.ledgo.data.DatabaseProvider
import com.marsanix.ledgo.data.TopicDao
import com.marsanix.ledgo.data.TopicEntity
import com.marsanix.ledgo.data.UserProgressDao
import com.marsanix.ledgo.data.UserProgressEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.marsanix.ledgo.data.AppSettingsEntity
import kotlinx.coroutines.launch


// ViewModel untuk manajemen data dan logika

class DigitalEconomyViewModel(private val databaseProvider: DatabaseProvider, application: Application) : AndroidViewModel(application) {

    private val topicDao = databaseProvider.topicDao
    private val userProgressDao = databaseProvider.userProgressDao
    private val settingsDao = databaseProvider.settingsDao

    private val _topics = MutableLiveData<List<TopicEntity>>(emptyList())
    var topics: LiveData<List<TopicEntity>> = _topics

    private val _userProgress = MutableLiveData<Map<Int, UserProgressEntity>?>(emptyMap())
    var userProgress: LiveData<Map<Int, UserProgressEntity>?> = _userProgress

    private val _settings = MutableStateFlow<Map<Int, AppSettingsEntity>>(emptyMap())
    val appSettings: StateFlow<Map<Int, AppSettingsEntity>> = _settings

    init {

        loadTopics()
        // loadUserProgress()
    }

    fun loadTopics() = viewModelScope.launch {
        topics = topicDao.getAllTopics()
        // Proses data topics
    }

    fun loadUserProgress(id: Int) = viewModelScope.launch {
        val progress = userProgressDao.getUserProgress(id)
        Log.e("_PROGRESS", progress.toString())
        // Proses data topics
    }

    fun insert(materi: TopicEntity) = viewModelScope.launch {
        topicDao.insert(materi)
    }

    fun updateTopicProgress(topicId: Int, completedModules: Int, score: Int) = viewModelScope.launch {
        val progress = UserProgressEntity(topicId, completedModules, score)
        userProgressDao.updateProgress(progress)
    }

    fun upsertSetting(materiSetting: AppSettingsEntity) = viewModelScope.launch {
        settingsDao.upsertSetting(materiSetting)
    }

    private val _materi = MutableLiveData<TopicEntity>()
    val materi: LiveData<TopicEntity> get() = _materi
    fun getMateri(id: Int) = viewModelScope.launch {
        val result = topicDao.getTopicById(id)
        _materi.postValue(result)
    }

    private val _materiSetting = MutableLiveData<AppSettingsEntity>()
    val materiSetting: LiveData<AppSettingsEntity> get() = _materiSetting
    fun getMateriSetting(code: String) = viewModelScope.launch {
        val result = settingsDao.getSettingByCode(code)
        _materiSetting.postValue(result)
    }

    private val _lastActivity = MutableLiveData<AppSettingsEntity>()
    val lastActivity: LiveData<AppSettingsEntity> get() = _lastActivity
    fun getLastActivity() = viewModelScope.launch {
        val result = settingsDao.getSettingByCode("aktivitas_terakhir")
        _lastActivity.postValue(result)
    }

    /*
    private fun loadTopics() {
        // Contoh inisialisasi topik
        val defaultTopics = listOf(
            TopicEntity(1, "Konsep Dasar Ekonomi Digital",
                "Pengantar fundamental ekonomi digital", 1, 5),
            TopicEntity(2, "Transformasi Bisnis Digital",
                "Strategi dan model bisnis digital", 2, 6),
            TopicEntity(3, "Teknologi Pendorong Ekonomi Digital",
                "AI, Blockchain, dan Inovasi Teknologi", 3, 7)
        )
        topicDao.insertTopics(defaultTopics)
        _topics.value = topicDao.getAllTopics()
    }
    */



     fun loadUserProgress() {
         loadTopics()
         Log.e("_progressMap",topics.value.toString())
         val progressMap = topics.value?.associate { topic ->

            topic.id to (userProgressDao.getUserProgress(topic.id)
                ?: UserProgressEntity(topic.id, 0, 0))
         }

//        if (progressMap != null) {
//
//            _userProgress.value = progressMap
//        }
    }

    /*
    fun updateTopicProgress(topicId: Int, completedModules: Int, score: Int) {
        val updatedProgress = UserProgressEntity(topicId, completedModules, score)
        userProgressDao.updateProgress(updatedProgress)
        loadUserProgress()
    }
     */

}