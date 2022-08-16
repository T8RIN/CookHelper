package ru.tech.cookhelper.domain.use_case.get_fridge_list

import kotlinx.coroutines.flow.Flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.Product
import javax.inject.Inject

class GetFridgeListUseCase @Inject constructor(
    //TODO: Normal repository
) {

    operator fun invoke(): Flow<Action<List<Product>>> {
        TODO()
    }

}