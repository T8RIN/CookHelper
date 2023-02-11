package ru.tech.cookhelper.presentation.fullscreen_image_pager

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import ru.tech.cookhelper.domain.model.FileData

val FileDataSaver: Saver<MutableState<List<FileData>>, String> = Saver(
    save = {
        it.value.joinToString("*_*_*_*") { "${it.id}!_*_*_!${it.link}" }
    },
    restore = {
        mutableStateOf(
            it.split("*_*_*_*").map {
                val t = it.split("!_*_*_!")
                FileData(t[1], t[0])
            }
        )
    }
)