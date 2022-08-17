package ru.tech.cookhelper.data.remote.utils

import ru.tech.cookhelper.domain.utils.Domain

interface Dto {
    fun asDomain(): Domain
}