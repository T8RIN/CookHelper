package ru.tech.cookhelper.presentation.forum_discussion.viewModel

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.blur
import ru.tech.cookhelper.presentation.ui.utils.android.ImageUtils.signature
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import javax.inject.Inject

@HiltViewModel
class ForumDiscussionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _blurredBitmap: MutableState<Bitmap?> = mutableStateOf(null)
    var blurredBitmap: Bitmap? by _blurredBitmap

    private var bitmapSignature: String = ""

    fun blur(bitmap: Bitmap) {
        bitmap.blur(scope = viewModelScope) {
            val sign = it?.signature() ?: ""
            if (bitmapSignature != sign) {
                _blurredBitmap.update { it }
                bitmapSignature = it?.signature() ?: ""
            } else it?.recycle()
        }
    }

}
