package ru.tech.cookhelper.data.local.utils

import ru.tech.cookhelper.domain.utils.Domain

interface DatabaseEntity {
    fun asDomain(): Domain
}