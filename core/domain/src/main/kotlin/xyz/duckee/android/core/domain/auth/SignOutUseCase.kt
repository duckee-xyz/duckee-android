package xyz.duckee.android.core.domain.auth;

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import dagger.Reusable
import xyz.duckee.android.core.data.AuthRepository
import xyz.duckee.android.core.data.PreferencesRepository
import xyz.duckee.android.core.model.Credentials
import xyz.duckee.android.core.model.User
import javax.inject.Inject

@Reusable
class SignOutUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {
    suspend operator fun invoke() = preferencesRepository.clearCredentials()
}

