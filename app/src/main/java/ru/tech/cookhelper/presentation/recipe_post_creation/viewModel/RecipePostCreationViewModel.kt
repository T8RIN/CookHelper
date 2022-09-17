package ru.tech.cookhelper.presentation.recipe_post_creation.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.use_case.get_user.GetUserUseCase
import ru.tech.cookhelper.presentation.ui.utils.compose.StateUtils.update
import javax.inject.Inject

@HiltViewModel
class RecipePostCreationViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _allProducts: MutableState<List<Product>> = mutableStateOf(emptyList())
    val allProducts: List<Product> by _allProducts

    private val _products: MutableState<List<Product>> = mutableStateOf(emptyList())
    val products: List<Product> by _products

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: User? by _user

    private val _categories: MutableState<List<String>> = mutableStateOf(emptyList())
    val categories: List<String> by _categories

    init {
        getUserUseCase()
            .onEach { _user.update { it } }
            .launchIn(viewModelScope)

        _categories.value = List(15) { "Категория блюда с порядковым номером $it" }
        _allProducts.value = List(30) { Product(it, "Продукт $it", mimeType = "грамм") }
    }

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
        //TODO: Send data to server
    }

    fun setProducts(newProducts: List<Product>) {
        _products.update { newProducts }
    }

}