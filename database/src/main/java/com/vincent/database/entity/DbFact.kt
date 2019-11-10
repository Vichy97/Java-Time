package com.vincent.database.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DbFact(
    @Id var id: Long = 0,
    val body: String = ""
)