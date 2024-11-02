package com.jmuthuan.treely.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class AppDataContainer {

    private val FIRESTORE_EMULATOR_HOST = "10.0.2.2"
    private val FIRESTORE_EMULATOR_PORT = 8080

    private val db = FirebaseFirestore.getInstance()

    private val emulator = db
        .useEmulator(FIRESTORE_EMULATOR_HOST, FIRESTORE_EMULATOR_PORT)

    init {
        Log.d("MTH", "Network enebling...")
        db.enableNetwork()
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Log.d("MTH", "Network enabled")
                } else {
                    Log.w("MTH", "Network enebling failed", task.exception)
                }
            }
    }

    fun getDatabase(): FirebaseFirestore {
        return db
    }

}