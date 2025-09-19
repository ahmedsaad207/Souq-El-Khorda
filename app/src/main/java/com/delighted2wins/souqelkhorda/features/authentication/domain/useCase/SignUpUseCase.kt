package com.delighted2wins.souqelkhorda.features.authentication.domain.useCase

import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.domain.repo.IAuthenticationRepo
import javax.inject.Inject

class SignUpUseCase @Inject constructor(val repo: IAuthenticationRepo) {
    suspend operator fun invoke(signUpRequestDto: SignUpRequestDto) = repo.signUp(signUpRequestDto)
}