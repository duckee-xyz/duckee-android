package xyz.duckee.android.core.domain.auth;

import dagger.Reusable
import xyz.duckee.android.core.data.PreferencesRepository
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {
    suspend operator fun invoke() = preferencesRepository.clearCredentials()
}

