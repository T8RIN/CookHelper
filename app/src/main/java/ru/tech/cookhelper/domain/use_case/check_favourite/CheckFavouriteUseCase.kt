package ru.tech.cookhelper.domain.use_case.check_favourite

import javax.inject.Inject

class CheckFavouriteUseCase @Inject constructor(
    //TODO: Normal repository
) {

    suspend operator fun invoke(id: Int): Boolean {
        TODO()
    }

}