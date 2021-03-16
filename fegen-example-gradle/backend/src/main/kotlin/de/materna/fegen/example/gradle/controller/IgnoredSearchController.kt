package de.materna.fegen.example.gradle.controller

import de.materna.fegen.example.gradle.entity.Contact
import de.materna.fegen.example.gradle.repository.ContactRepository
import de.materna.fegen.util.spring.annotation.FegenIgnore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.BasePathAwareController
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@FegenIgnore
@BasePathAwareController
@RequestMapping(path = ["/ignored/search"])
open class IgnoredSearchController {

    @Autowired
    lateinit var contactRepository: ContactRepository

    @RequestMapping("contactsByRegexIgnored")
    fun contactsByRegexIgnored(
        @RequestParam(name = "nameRegex") name: String
    ): ResponseEntity<CollectionModel<EntityModel<Contact.BaseProjection>>> {
        val regex = Regex(name)
        val result = contactRepository.getAllBaseProjected().filter {
            regex.containsMatchIn(it.firstName) || regex.containsMatchIn(it.lastName)
        }
        return ResponseEntity.ok(CollectionModel.wrap(result))
    }

    @FegenIgnore
    @RequestMapping("ignoredContactsByRegexIgnored")
    fun ignoredContactsByRegexIgnored(
        @RequestParam(name = "nameRegex") name: String
    ): ResponseEntity<CollectionModel<EntityModel<Contact.BaseProjection>>> {
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
}