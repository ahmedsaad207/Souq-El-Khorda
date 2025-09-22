package com.delighted2wins.souqelkhorda.features.history.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
): HistoryRemoteDataSource {
}