package ru.tech.cookhelper.domain.use_case

import ru.tech.cookhelper.domain.repository.FridgeRepository
import javax.inject.Inject

class GetMatchedRecipesUseCase @Inject constructor(
    private val fridgeRepository: FridgeRepository
) {
    suspend operator fun invoke(token: String) = fridgeRepository.getMatchedRecipes(token)
}