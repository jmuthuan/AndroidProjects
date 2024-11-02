package com.jmuthuan.treely.data

import androidx.compose.ui.graphics.painter.Painter
import com.jmuthuan.treely.utils.Gender

data class FamilyMember(
    val name: String = "",
    val birthday: String = "",
    val gender: Gender = Gender.OTHER,
    val picture: Painter? = null
)
