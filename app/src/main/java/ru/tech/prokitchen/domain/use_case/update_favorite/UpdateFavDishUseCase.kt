package ru.tech.prokitchen.domain.use_case.update_favorite

import ru.tech.prokitchen.domain.repository.ProKitchenRepository
import javax.inject.Inject

class UpdateFavDishUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    suspend operator fun invoke(id: Int, fav: Boolean) {
        repository.updateFav(id, fav)
    }

}