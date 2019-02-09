package com.rdas

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class  MainKtApplication

fun main(args: Array<String>) {
    SpringApplication.run(MainKtApplication::class.java, *args)
}