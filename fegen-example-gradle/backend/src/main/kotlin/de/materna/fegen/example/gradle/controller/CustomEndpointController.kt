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

import de.materna.fegen.example.gradle.entity.Address
import de.materna.fegen.example.gradle.entity.Contact
import de.materna.fegen.example.gradle.repository.AddressRepository
import de.materna.fegen.example.gradle.repository.ContactRepository
import de.materna.fegen.example.gradle.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/custom/contacts")
open class CustomEndpointController(
        @Autowired val userRepository: UserRepository,
        @Autowired val contactRepository: ContactRepository,
        @Autowired val addressRepository: AddressRepository
) {
    @Transactional
    @RequestMapping("createOrUpdate", method = [RequestMethod.POST])
    open fun createOrUpdateContact(
            @RequestParam userName: String,
            @RequestParam firstName: String,
            @RequestParam lastName: String,
            @RequestParam number: String,
            @RequestParam street: String,
            @RequestParam zip: String,
            @RequestParam city: String,
            @RequestParam country: String
    ): ResponseEntity<String> {
        val user = userRepository.findUserByName(userName) ?: return ResponseEntity.notFound().build()

        var contact = contactRepository.findByNames(firstName, lastName) ?: Contact()

        contact.owner = user
        contact.firstName = firstName
        contact.lastName = lastName
        contact.number = number.ifBlank { null }

        if (arrayOf(street, zip, city, country).any { it.isNotBlank() }) {
            var address = contact.address ?: Address()
            address.street = street
            address.zip = zip
            address.city = city
            address.country = country
            address = addressRepository.save(address)
            contact.address = address
        } else {
            val address = contact.address
            if (address != null) {
                contact.address = null
                addressRepository.delete(address)
            }
        }

        contactRepository.save(contact)

        return ResponseEntity.ok("")
    }
}