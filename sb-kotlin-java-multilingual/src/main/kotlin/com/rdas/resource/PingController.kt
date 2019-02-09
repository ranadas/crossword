package com.rdas.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {
    @GetMapping("/hello")
    fun hello() : String {
        return "hello world \n"
    }
}