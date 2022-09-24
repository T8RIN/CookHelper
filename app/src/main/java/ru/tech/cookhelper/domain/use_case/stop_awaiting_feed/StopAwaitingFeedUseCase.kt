package ru.tech.cookhelper.domain.use_case.stop_awaiting_feed

import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class StopAwaitingFeedUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.stopAwaitingFeed()
}
