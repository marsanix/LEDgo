package com.marsanix.ledgo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity untuk progress pengguna
@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val topicId: Int,
    @ColumnInfo(name = "completed_modules") val completedModules: Int,
    @ColumnInfo(name = "total_score") val totalScore: Int
)
