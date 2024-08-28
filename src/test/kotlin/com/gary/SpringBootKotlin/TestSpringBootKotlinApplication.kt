package com.gary.SpringBootKotlin

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<SpringBootKotlinApplication>().with(TestcontainersConfiguration::class).run(*args)
}
