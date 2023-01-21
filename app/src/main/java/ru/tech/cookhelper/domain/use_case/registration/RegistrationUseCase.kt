package ru.tech.cookhelper.domain.use_case.registration

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tech.cookhelper.core.Action
import ru.tech.cookhelper.domain.model.FileData
import ru.tech.cookhelper.domain.model.User
import ru.tech.cookhelper.domain.repository.UserRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        name: String,
        surname: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Action<User>>/* = userRepository.registerWith(name, surname, nickname, email, password) */ {
        /*TODO: remove when api will work again */
        return flow {
            emit(
                Action.Success(
                    User(
                        name = name,
                        surname = surname,
                        nickname = nickname,
                        email = email,
                        id = 0L,
                        avatar = listOf(
                            FileData(
                                link = "https://avatars.githubusercontent.com/u/52178347?s=120&v=4",
                                id = "ava"
                            )
                        ),
                        fridge = emptyList(),
                        verified = true,
                        lastSeen = System.currentTimeMillis(),
                        token = "",
                        status = ""
                    )
                )
            )
        }
    }
}