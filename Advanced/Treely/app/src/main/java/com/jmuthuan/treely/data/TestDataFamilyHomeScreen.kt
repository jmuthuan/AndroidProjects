package com.jmuthuan.treely.data

import com.jmuthuan.treely.utils.Gender

val family = mutableListOf<FamilyMember>(
    FamilyMember(
        name = "Bruce Wayne",
        birthday = "01-01-1960",
        gender = Gender.MALE
        ),
    FamilyMember(
        name = "Mary",
        birthday = "12-25-1985",
        gender = Gender.FEMALE
    ),
    FamilyMember(
        name = "Alex Who",
        birthday = "11-20-2021",
        gender = Gender.OTHER
    )

)