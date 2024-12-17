package com.marsanix.ledgo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE topicId = :topicId")
    fun getUserProgress(topicId: Int): UserProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateProgress(progress: UserProgressEntity)
}