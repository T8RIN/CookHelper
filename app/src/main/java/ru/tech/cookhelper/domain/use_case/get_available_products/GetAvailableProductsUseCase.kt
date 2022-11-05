package ru.tech.cookhelper.domain.use_case.get_available_products

import ru.tech.cookhelper.domain.repository.IngredientsRepository
import javax.inject.Inject

class GetAvailableProductsUseCase @Inject constructor(
    private val ingredientsRepository: IngredientsRepository
) {
    suspend operator fun invoke() = ingredientsRepository.getAvailableProducts()
}