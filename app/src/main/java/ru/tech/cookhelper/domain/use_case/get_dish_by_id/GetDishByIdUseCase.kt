package ru.tech.cookhelper.domain.use_case.get_dish_by_id

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Recipe
import javax.inject.Inject

class GetDishByIdUseCase @Inject constructor(
    //TODO: Normal repository
) {

    operator fun invoke(id: Int): Flow<Action<Recipe>> {
        TODO()
    }

}