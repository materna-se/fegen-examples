package de.materna.fegen.example.gradle.controller

import de.materna.fegen.util.spring.annotation.FegenIgnore
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@FegenIgnore
@RestController
@RequestMapping("/api/custom/ignoredTestController")
class IgnoredTestRestController {

    @GetMapping("endpoint")
    fun endpoint(): ResponseEntity<String> =
        ResponseEntity.ok("SomeText")

    @FegenIgnore
    @GetMapping("ignoredEndpoint")
    @ResponseBody
    fun ignoredEndpoint(): ResponseEntity<String> =
        ResponseEntity.ok("SomeText")

}