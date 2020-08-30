package com.example.contactsbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class ContactsbookApplication

fun main(args: Array<String>) {
    runApplication<ContactsbookApplication>(*args)
}
