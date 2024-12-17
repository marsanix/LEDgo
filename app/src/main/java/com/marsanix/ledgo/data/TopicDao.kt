package com.marsanix.ledgo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// DAO (Data Access Object) untuk manajemen data
@Dao
interface TopicDao {
    @Query("SELECT * FROM digital_economy_topics")
    fun getAllTopics(): LiveData<List<TopicEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(materiEntity: TopicEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopics(topics: List<TopicEntity>)

    @Query("SELECT * FROM digital_economy_topics WHERE id = :topicId")
    fun getTopicById(topicId: Int): TopicEntity
}