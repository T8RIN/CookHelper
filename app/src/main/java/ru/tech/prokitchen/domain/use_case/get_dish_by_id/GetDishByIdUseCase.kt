package ru.tech.prokitchen.domain.use_case.get_dish_by_id

import kotlinx.coroutines.flow.Flow
import ru.tech.prokitchen.core.Action
import ru.tech.prokitchen.domain.model.Recipe
import ru.tech.prokitchen.domain.repository.ProKitchenRepository
import javax.inject.Inject

class GetDishByIdUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    operator fun invoke(id: Int): Flow<Action<Recipe>> {
        return repository.getDishById(id)
    }

}