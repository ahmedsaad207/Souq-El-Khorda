package com.delighted2wins.souqelkhorda.features.authentication.domain.useCase

import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import javax.inject.Inject

class CashUserCase @Inject constructor(val repo: IAuthenticationRepo) {
    suspend operator fun invoke(user: AuthUser) = repo.cashUserData(user)
}