package com.jmuthuan.treely.data.repository

import com.jmuthuan.treely.shared.PersonData
import kotlinx.coroutines.flow.Flow


interface DatabaseRepository {
    fun addFamilyMember(person: PersonData)

    fun updateFamilyMember(person: PersonData)

    fun deleteFamilyMember(personId: String)

    fun getMemeber(personId: String): Flow<PersonData>

    fun getAllData(): Flow<List<PersonData>>
}