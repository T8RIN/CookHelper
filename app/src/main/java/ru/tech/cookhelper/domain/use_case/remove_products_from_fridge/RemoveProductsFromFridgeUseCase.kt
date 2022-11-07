package ru.tech.cookhelper.domain.use_case.remove_products_from_fridge

import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.repository.FridgeRepository
import javax.inject.Inject

class RemoveProductsFromFridgeUseCase @Inject constructor(
    private val fridgeRepository: FridgeRepository
) {
    suspend operator fun invoke(
        token: String,
        fridge: List<Product>
    ) = fridgeRepository.removeProductsFromFridge(token, fridge)
}