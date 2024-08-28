package com.gary.SpringBootKotlin.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Person(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Int? = null,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var age: Int,
)
