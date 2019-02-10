package com.rdas.resource

import com.rdas.service.HzcastService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController(val  hzcastService: HzcastService) {

    @GetMapping("/hello")
    fun hello() : String {
        val sample = hzcastService.sample()
        return "hello world , $sample\n"
    }
}