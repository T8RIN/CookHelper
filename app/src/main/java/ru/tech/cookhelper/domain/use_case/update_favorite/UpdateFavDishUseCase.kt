package ru.tech.cookhelper.domain.use_case.update_favorite

import ru.tech.cookhelper.domain.repository.ProKitchenRepository
import javax.inject.Inject

class UpdateFavDishUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    suspend operator fun invoke(id: Int, fav: Boolean) {
        repository.updateFav(id, fav)
    }

}