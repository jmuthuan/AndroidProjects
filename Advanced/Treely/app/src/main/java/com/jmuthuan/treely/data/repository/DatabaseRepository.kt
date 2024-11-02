package com.jmuthuan.treely.data.repository

import com.jmuthuan.treely.shared.PersonData
import kotlinx.coroutines.flow.Flow


interface DatabaseRepository {
    fun addFamilyMember(person: PersonData)

    fun getAllData(): Flow<List<PersonData>>
}