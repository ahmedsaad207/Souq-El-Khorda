package com.delighted2wins.souqelkhorda.features.buyers.data.remote

import com.delighted2wins.souqelkhorda.core.AppConstant
import com.delighted2wins.souqelkhorda.features.buyers.data.model.BuyerDto
import com.delighted2wins.souqelkhorda.features.buyers.presentation.contract.BuyerState
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class BuyerRemoteDataSourceImp @Inject constructor(
    val firebaseDb: FirebaseFirestore
) : IBuyerRemoteDataSource {
    override suspend fun registerBuyer(buyerModel: BuyerDto): Flow<BuyerState> = flow {
        emit(BuyerState.Loading)
        try {
            firebaseDb.collection(AppConstant.FIRESTORE_BUYER_COLLECTION_NAME)
                .document(buyerModel.buyerID)
                .set(buyerModel)
                .await()
            emit(BuyerState.RegisterSuccess(buyerModel))

        }catch (e: FirebaseAuthUserCollisionException) {
            emit(BuyerState.Error("Email already exists"))
        }
        catch (e: Exception) {
            emit(BuyerState.Error(e.message ?: "Unexpected error"))
        }
    }

    override suspend fun getNearstBuyers(): Flow<BuyerState> = flow {
        emit(BuyerState.Loading)
        try {
            val snapshot = firebaseDb.collection(AppConstant.FIRESTORE_BUYER_COLLECTION_NAME)
                .get()
                .await()

            val buyers = snapshot.toObjects(BuyerDto::class.java)
            emit(BuyerState.SuccessLoading(buyers))

        } catch (e: Exception) {
            emit(BuyerState.Error(e.message ?: "Error fetching buyers"))
        }
    }

    override suspend fun isBuyer(userID: String): Boolean {
        return try {
            val doc = firebaseDb.collection(AppConstant.FIRESTORE_BUYER_COLLECTION_NAME)
                .document(userID)
                .get()
                .await()
            doc.exists()
        } catch (e: Exception) {
            false
        }
    }
}