package ru.tech.cookhelper.domain.use_case.update_fridge

import ru.tech.cookhelper.domain.repository.ProKitchenRepository
import javax.inject.Inject

class UpdateFridgeUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    suspend operator fun invoke(id: Int, inFridge: Boolean) {
        repository.updateProduct(id, inFridge)
    }

}