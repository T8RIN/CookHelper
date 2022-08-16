package ru.tech.cookhelper.domain.use_case.get_favourites

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Recipe
import javax.inject.Inject

class GetFavouriteDishesUseCase @Inject constructor(
    //TODO: Normal repository
) {

    operator fun invoke(): Flow<Action<List<Recipe>>> {
        TODO()
    }

}