package ru.tech.prokitchen.domain.use_case.get_fridge_list

import kotlinx.coroutines.flow.Flow
import ru.tech.prokitchen.core.Action
import ru.tech.prokitchen.domain.model.Product
import ru.tech.prokitchen.domain.repository.ProKitchenRepository
import javax.inject.Inject

class GetFridgeListUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    operator fun invoke(): Flow<Action<List<Product>>> {
        return repository.getFridgeList()
    }

}