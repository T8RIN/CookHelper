package ru.tech.cookhelper.domain.use_case.get_available_products

import ru.tech.cookhelper.domain.repository.FridgeRepository
import javax.inject.Inject

class GetAvailableProductsUseCase @Inject constructor(
    private val fridgeRepository: FridgeRepository
) {
    suspend operator fun invoke() = fridgeRepository.getAvailableProducts()
}