package com.gary.SpringBootKotlin.controller

import com.gary.SpringBootKotlin.dto.Person
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/person")
class PersonController {
    @PostMapping
    fun createPerson(
        @RequestBody @Valid person: Person,
    ) {
        println("Person created")
    }

    @GetMapping
    fun getPerson(): Person = Person(1, "Gary", 25)
}
