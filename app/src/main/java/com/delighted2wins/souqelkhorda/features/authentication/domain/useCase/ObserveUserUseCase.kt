package com.delighted2wins.souqelkhorda.features.authentication.domain.useCase

import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import javax.inject.Inject

class ObserveUserUseCase @Inject constructor(
    private val repo: IAuthenticationRepo
) {
    operator fun invoke() =  repo.userFlow
}