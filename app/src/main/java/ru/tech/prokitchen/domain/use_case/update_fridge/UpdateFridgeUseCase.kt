package ru.tech.prokitchen.domain.use_case.update_fridge

import ru.tech.prokitchen.domain.repository.ProKitchenRepository
import javax.inject.Inject

class UpdateFridgeUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    suspend operator fun invoke(id: Int, inFridge: Boolean) {
        repository.updateProduct(id, inFridge)
    }

}