package ru.tech.prokitchen.domain.use_case.check_favourite

import ru.tech.prokitchen.domain.repository.ProKitchenRepository
import javax.inject.Inject

class CheckFavouriteUseCase @Inject constructor(
    private val repository: ProKitchenRepository
) {

    suspend operator fun invoke(id: Int): Boolean {
        return repository.checkFavoriteId(id)
    }

}