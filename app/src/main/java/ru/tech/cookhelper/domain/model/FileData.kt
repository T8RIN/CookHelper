package ru.tech.cookhelper.domain.model

import ru.tech.cookhelper.domain.utils.Domain

data class FileData(
    val link: String,
    val id: String
) : Domain

//data class FileData(
//    val id: Long,
//    val name: String,
//    val link: String,
//    val type: String
//)
