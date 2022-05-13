package ru.tech.cookhelper.domain.use_case.update_fridge

import ru.tech.cookhelper.domain.repository.CookHelperRepository
import javax.inject.Inject

class UpdateFridgeUseCase @Inject constructor(
    private val repository: CookHelperRepository
) {

    suspend operator fun invoke(id: Int, inFridge: Boolean) {
        repository.updateProduct(id, inFridge)
    }

}