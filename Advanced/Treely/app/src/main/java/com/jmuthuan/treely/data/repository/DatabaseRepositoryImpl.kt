package com.jmuthuan.treely.data.repository


import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import com.jmuthuan.treely.shared.PersonData
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Date

class DatabaseRepositoryImpl(private val db: FirebaseFirestore) : DatabaseRepository {

    private lateinit var data: MutableList<Map<String, Any>>

    override fun addFamilyMember(person: PersonData) {

        val member = hashMapOf(
            "name" to person.name,
            "gender" to person.gender,
            "photo" to person.photo,
            "location" to person.location,
            "birthday" to person.birthday,
            "extras" to person.extras
        )

        Log.d("MTH", "Add Data Button: ")

        // Add a new document with a generated ID
        db.collection("family").add(member)
            .addOnSuccessListener { documentReference ->
                Log.d("MTH", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("MTH", "Error adding document", e)
            }
            .addOnCanceledListener {
                Log.w("MTH", "Cancel adding document")
            }
            .addOnCompleteListener {
                Log.w("MTH", "Complete adding document: ${it.isComplete}")
            }
    }

    override fun getAllData(): Flow<List<PersonData>> = callbackFlow{
        Log.d("MTH", "Reading data...")

            db.collection("family").addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (value != null && !value.isEmpty) {
                    trySend(element = value.toObjects<PersonData>())
                }
            }

            awaitClose { this.cancel(message = "Error encountered. Please try again...") }
//            .get()
//            .addOnSuccessListener { result ->

//                val allData = mutableListOf<Map<String, Any>>()

//                for (document in result) {
//                    Log.d("MTH", "${document.id} => ${document.data}")
//                    allData.add(mapOf(document.id to document.data.toMap()))
//                }
//                trySend(element = allData).isSuccess
//            }
//            .addOnFailureListener { exception ->
//                Log.w("MTH", "Error getting documents.", exception)
//            }
//            .addOnCompleteListener {
//                Log.d("MTH", "Reading data complete: ${it.isComplete}")
//            }
    }
}