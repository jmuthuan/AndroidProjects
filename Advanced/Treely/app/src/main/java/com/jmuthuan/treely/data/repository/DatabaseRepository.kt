package com.jmuthuan.treely.data.repository

import com.jmuthuan.treely.shared.PersonData
import com.jmuthuan.treely.ui.persons.RelationshipData
import kotlinx.coroutines.flow.Flow


interface DatabaseRepository {
    fun addFamilyMember(person: PersonData, relationshipData: RelationshipData? = null)

//    fun addRelationShip(personId: String, relationship: String)

    fun updateFamilyMember(person: PersonData)

    fun deleteFamilyMember(personId: String)

    fun getMemeber(personId: String): Flow<PersonData>

    fun getAllData(): Flow<List<PersonData>>
}