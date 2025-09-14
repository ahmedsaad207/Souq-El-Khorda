package com.delighted2wins.souqelkhorda.features.authentication.domain.useCase

import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import javax.inject.Inject

class LogoutUseCase@Inject constructor(val repo: IAuthenticationRepo) {
     operator fun invoke() = repo.logout()
}