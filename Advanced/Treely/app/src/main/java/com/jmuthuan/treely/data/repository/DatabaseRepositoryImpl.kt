package com.jmuthuan.treely.data.repository


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.jmuthuan.treely.shared.PersonData
import com.jmuthuan.treely.ui.persons.RelationshipData
import com.jmuthuan.treely.utils.Gender
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DatabaseRepositoryImpl(private val db: FirebaseFirestore) : DatabaseRepository {

//    private lateinit var data: MutableList<Map<String, Any>>

    override fun addFamilyMember(person: PersonData, relationshipData: RelationshipData?) {

        val member = hashMapOf<String, Any?>(
            "name" to person.name,
            "gender" to person.gender,
            "photo" to person.photo,
            "location" to person.location,
            "birthday" to person.birthday,
            "extras" to person.extras,
        )

        if(relationshipData != null) {
            member[relationshipData.relationship] = listOf(relationshipData.personId)
        }

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

    override fun updateFamilyMember(person: PersonData) {
        val member = hashMapOf(
            "name" to person.name,
            "gender" to person.gender,
            "photo" to person.photo,
            "location" to person.location,
            "birthday" to person.birthday,
            "extras" to person.extras
        )

        Log.d("MTH", "Add Data Button: ")

        // Update an existing document
        db.collection("family").document(person.key)
            .set(member)
            .addOnSuccessListener { documentReference ->
                Log.d("MTH", "DocumentSnapshot updated with ID: ${documentReference}")
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

    override fun deleteFamilyMember(personId: String) {
        db.collection("family")
            .document(personId)
            .delete()
            .addOnSuccessListener {
                Log.d("MTH", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener {
                    e -> Log.w("MTH", "Error deleting document", e)
            }
    }

    override fun getMemeber(personId: String): Flow<PersonData> = callbackFlow {
        db.collection("family")
            .document(personId)
            .get()
            .addOnSuccessListener {document ->
                if (document != null) {
                    trySend( element = document.toObject<PersonData>()!!
                    )
                } else {
                    Log.d("MTH", "No such document")
                }
            }
            .addOnFailureListener {exception ->
                Log.w("MTH", "Error getting document.", exception)
            }

        awaitClose { this.cancel(message = "Error encountered. Please try again...") }
    }
    override fun getAllData(): Flow<List<PersonData>> = callbackFlow{
        Log.d("MTH", "Reading data...")

        db.collection("family")
            .get()
            .addOnSuccessListener {result ->
            val sendList = mutableListOf<PersonData>()
                for (document in result) {
                    sendList.add(
                        PersonData(
                            name = document.data["name"].toString(),
                            gender = Gender.valueOf(document.data["gender"].toString()),
                            birthday = document.data["birthday"].toString(),
                            photo = document.data["photo"].toString(),
                            extras = document.data["extras"].toString(),
                            location = document.data["location"].toString(),
                            key = document.id
                        )
                    )
                    Log.d("MTH", "${document.id} => ${document.data}")
                }
                trySend(element = sendList)
            }
            .addOnFailureListener {exception ->
                Log.w("MTH", "Error getting documents.", exception)
            }

            awaitClose { this.cancel(message = "Error encountered. Please try again...") }
    }
}