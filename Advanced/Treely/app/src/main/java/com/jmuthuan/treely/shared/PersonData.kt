package com.jmuthuan.treely.shared

import android.net.Uri
import com.jmuthuan.treely.utils.Gender
import java.util.Date

data class PersonData(
    var name: String = "",
    var gender: Gender = Gender.OTHER,
//    var photo: Uri? =
//        Uri.parse("android.resource://com.jmuthuan.treely/drawable/avatar_profile"),
    var photo: String = "android.resource://com.jmuthuan.treely/drawable/avatar_profile",
    var location: String = "",
    var birthday: String = "",
    var extras: String = "",
    var key: String = ""
)
