package com.marsanix.ledgo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Definisi Entity untuk Room Database
@Entity(tableName = "digital_economy_topics")
data class TopicEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "slug") val slug: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "youtube") val youtube: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "module_count") val moduleCount: Int,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean
)