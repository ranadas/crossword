package com.rdas.resource

import com.rdas.entity.Student
import com.rdas.service.CachedService
import com.rdas.service.HzcastService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController(val  hzcastService: HzcastService, val cachedService: CachedService) {

    @GetMapping("/hello")
    fun hello() : String {
        val sample = hzcastService.sample()
        return "hello world , $sample\n"
    }


    @GetMapping("/search")
    fun search(@RequestParam(value = "cntain") beginChars: String) : List<Student> {
        return cachedService.search(beginChars)
    }

    @GetMapping("/searchById")
    fun searchById(@RequestParam(value = "id") id: Long) : Student {
        return hzcastService.findById(id);
    }
}