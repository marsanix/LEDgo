package com.marsanix.ledgo.data

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AppSettingsDao {
    @Upsert
    fun upsertSetting(materiSettingEntity: AppSettingsEntity)

    @Query("SELECT * FROM settings WHERE code = :code")
    fun getSettingByCode(code: String): AppSettingsEntity
}

