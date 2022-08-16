package ru.tech.cookhelper.presentation.dish_details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import ru.tech.cookhelper.presentation.dish_details.viewModel.DishDetailsViewModel

@ExperimentalMaterial3Api
@Composable
fun DishDetailsScreen(
    id: Int,
    onBack: () -> Unit,
    viewModel: DishDetailsViewModel = hiltViewModel(
        defaultArguments = bundleOf("id" to id)
    )
) {

}

private val Double.s: String
    get() {
        return this.toString()
    }