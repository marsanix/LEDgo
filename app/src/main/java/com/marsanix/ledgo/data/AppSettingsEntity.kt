package com.marsanix.ledgo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class AppSettingsEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "value")
    val value: String

)