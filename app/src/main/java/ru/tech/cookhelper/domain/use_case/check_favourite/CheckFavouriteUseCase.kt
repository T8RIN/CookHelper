package ru.tech.cookhelper.domain.use_case.check_favourite

import ru.tech.cookhelper.domain.repository.CookHelperRepository
import javax.inject.Inject

class CheckFavouriteUseCase @Inject constructor(
    private val repository: CookHelperRepository
) {

    suspend operator fun invoke(id: Int): Boolean {
        return repository.checkFavoriteId(id)
    }

}