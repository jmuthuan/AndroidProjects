package com.jmuthuan.treely.data.repository

import com.google.firebase.auth.AuthResult
import com.jmuthuan.treely.shared.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>

}