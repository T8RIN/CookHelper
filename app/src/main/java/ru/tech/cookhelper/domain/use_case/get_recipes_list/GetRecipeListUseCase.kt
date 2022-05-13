package ru.tech.cookhelper.domain.use_case.get_recipes_list

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Recipe
import ru.tech.cookhelper.domain.repository.ProKitchenRepository
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    operator fun invoke(): Flow<Action<List<Recipe>>> {
        return repository.getCuisineList()
    }

}