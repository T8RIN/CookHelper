package ru.tech.cookhelper.presentation.recipe_post_creation.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import javax.inject.Inject

@HiltViewModel
class RecipePostCreationViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase
) : ViewModel() {

    fun sendRecipePost(
        label: String,
        imageUri: String,
        time: String,
        calories: String,
        proteins: String,
        fats: String,
        carbohydrates: String,
        category: String,
        steps: String
    ) {
        TODO()
    }

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _categories: MutableState<List<String>> = mutableStateOf(emptyList())
    val categories: State<List<String>> = _categories

    init {
        getUserUseCase()
            .onEach { _user.value = it }
            .launchIn(viewModelScope)

        _categories.value = List(15) { "Категория блюда с порядковым номером $it" }
    }

}