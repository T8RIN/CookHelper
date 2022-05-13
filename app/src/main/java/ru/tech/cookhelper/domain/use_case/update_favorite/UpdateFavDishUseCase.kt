package ru.tech.cookhelper.domain.use_case.update_favorite

import ru.tech.cookhelper.domain.repository.CookHelperRepository
import javax.inject.Inject

class UpdateFavDishUseCase @Inject constructor(
    private val repository: CookHelperRepository
) {

    suspend operator fun invoke(id: Int, fav: Boolean) {
        repository.updateFav(id, fav)
    }

}