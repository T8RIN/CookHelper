package ru.tech.cookhelper.domain.use_case.get_feed

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(token: String) = repository.getFeed(token)
}
