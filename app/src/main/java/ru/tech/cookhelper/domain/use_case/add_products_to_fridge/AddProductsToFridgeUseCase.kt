package ru.tech.cookhelper.domain.use_case.add_products_to_fridge

import ru.tech.cookhelper.domain.model.Product
import ru.tech.cookhelper.domain.repository.FridgeRepository
import javax.inject.Inject

class AddProductsToFridgeUseCase @Inject constructor(
    private val fridgeRepository: FridgeRepository
) {
    suspend operator fun invoke(
        token: String,
        fridge: List<Product>
    ) = fridgeRepository.addProductsToFridge(token, fridge)
}