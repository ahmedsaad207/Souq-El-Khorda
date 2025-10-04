package com.delighted2wins.souqelkhorda.features.authentication.data.remote

import com.delighted2wins.souqelkhorda.core.AppConstant
import com.delighted2wins.souqelkhorda.features.authentication.data.model.AuthUser
import com.delighted2wins.souqelkhorda.features.authentication.data.model.SignUpRequestDto
import com.delighted2wins.souqelkhorda.features.authentication.presentation.contract.AuthenticationState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthenticationRemoteDataSourceImp @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firebaseDb: FirebaseFirestore
) : IAuthenticationRemoteDataSource {
    override suspend fun signUpWithEmail(signUpRequestDto: SignUpRequestDto): Flow<AuthenticationState> =
        flow {
            emit(AuthenticationState.Loading)
            try {
                val authResult = firebaseAuth.createUserWithEmailAndPassword(
                    signUpRequestDto.email, signUpRequestDto.password
                ).await()

                val firebaseUser = authResult.user ?: throw Exception("No user found")

                val user = AuthUser(
                    id = firebaseUser.uid,
                    name = signUpRequestDto.name,
                    email = signUpRequestDto.email,
                    phone = signUpRequestDto.phone,
                    address = signUpRequestDto.address,
                    governorate = signUpRequestDto.governorate,
                    imageUrl = "",
                    area = signUpRequestDto.area
                )

                firebaseDb.collection(AppConstant.FIRESTORE_COLLECTION_NAEM)
                    .document(firebaseUser.uid)
                    .set(user)
                    .await()

                emit(AuthenticationState.Success(user))
            } catch (e: FirebaseAuthUserCollisionException) {
                emit(AuthenticationState.Error("Email is already in use. Please log in."))
            } catch (e: Exception) {
                emit(AuthenticationState.Error("SignUp failed: ${e.message}"))
            }
        }


    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): Flow<AuthenticationState> = flow {
        emit(AuthenticationState.Loading)
        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("No user found")
            val userDoc = firebaseDb.collection("users").document(firebaseUser.uid).get().await()
            val user = userDoc.toObject(AuthUser::class.java)
                ?: throw Exception("User data not found")
            emit(AuthenticationState.Success(user))
        } catch (e: Exception) {
            emit(AuthenticationState.Error("Login failed: ${e.message}"))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}