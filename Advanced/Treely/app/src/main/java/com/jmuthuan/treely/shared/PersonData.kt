package com.jmuthuan.treely.shared

import com.jmuthuan.treely.utils.Gender
import java.util.Date

data class PersonData(
    var name: String = "",
    var gender: Gender = Gender.OTHER,
    var photo: String = "", //TODO string resource, or int id for fetching data
    var location: String = "",
    var birthday: Date = Date(),
    var extras: String = ""
)
