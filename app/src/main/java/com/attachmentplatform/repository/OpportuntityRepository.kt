package com.attachmentplatform.student.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import android.util.Log
import javax.inject.Inject

class OpportunityRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance() // Firestore instance
    private val opportunitiesCollection = db.collection("opportunities") // Firestore collection reference

    // Function to create an opportunity in Firestore
    fun createOpportunity(
        opportunity: Opportunity,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Add the new opportunity to the Firestore collection
        opportunitiesCollection.add(opportunity)
            .addOnSuccessListener { documentReference ->
                Log.d("OpportunityRepo", "Opportunity added with ID: ${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("OpportunityRepo", "Error creating opportunity", e)
                onFailure(e)
            }
    }

    // Function to get opportunities from Firestore as a Flow
    fun getOpportunitiesStream(): Flow<List<Opportunity>> = callbackFlow {
        val listener = opportunitiesCollection
            .orderBy("createdAt", Query.Direction.DESCENDING) // Ordering by createdAt field
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("OpportunityRepo", "Error listening for opportunities", error)
                    close(error) // Close flow if error occurs
                    return@addSnapshotListener
                }

                // Convert Firestore snapshot to a list of Opportunity objects
                val opportunities = snapshot?.toObjects(Opportunity::class.java) ?: emptyList()
                trySend(opportunities) // Send the opportunities list to the flow
            }

        awaitClose {
            // Clean up listener when the flow is cancelled
            listener.remove()
            Log.d("OpportunityRepo", "Firestore snapshot listener removed.")
        }
    }
}
