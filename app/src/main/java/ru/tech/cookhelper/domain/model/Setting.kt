package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class Setting(
    val id: Int,
    val option: String
) : Domain
