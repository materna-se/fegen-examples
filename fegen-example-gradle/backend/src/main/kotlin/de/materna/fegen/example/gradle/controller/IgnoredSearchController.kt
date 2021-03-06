/**
 * Copyright 2020 Materna Information & Communications SE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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