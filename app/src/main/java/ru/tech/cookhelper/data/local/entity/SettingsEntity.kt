package ru.tech.cookhelper.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tech.cookhelper.data.local.utils.DatabaseEntity
import ru.tech.cookhelper.domain.model.Setting

@Entity
data class SettingsEntity(
    @PrimaryKey val id: Int,
    val option: String
) : DatabaseEntity {
    override fun asDomain(): Setting = Setting(id = id, option = option)
}