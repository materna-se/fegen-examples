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

import de.materna.fegen.example.gradle.component.ShutdownComponent
import de.materna.fegen.example.gradle.entity.Address
import de.materna.fegen.example.gradle.entity.Contact
import de.materna.fegen.example.gradle.entity.TestEntity
import de.materna.fegen.example.gradle.entity.User
import de.materna.fegen.example.gradle.repository.AddressRepository
import de.materna.fegen.example.gradle.repository.ContactRepository
import de.materna.fegen.example.gradle.repository.TestEntityRepository
import de.materna.fegen.example.gradle.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * This controller is not part of the example, but only used in automated tests
 */
@Controller
@RequestMapping("/api")
open class TestController(
        @Autowired
        val userRepository: UserRepository,

        @Autowired
        val contactRepository: ContactRepository,

        @Autowired
        val addressRepository: AddressRepository,

        @Autowired
        val testEntityRepository: TestEntityRepository,

        @Autowired
        val shutdownComponent: ShutdownComponent
) {


    /**
     * Sets the database to a predefined state
     */
    @RequestMapping("/setupTest", method = [RequestMethod.POST])
    @ResponseBody
    @Transactional
    open fun setupTests() {
        addressRepository.deleteAll()
        contactRepository.deleteAll()
        userRepository.deleteAll()
        testEntityRepository.deleteAll()

        val userOne = userRepository.save(User().apply {
            name = "UserOne"
            contacts = listOf()
        })
        val userTwo = userRepository.save(User().apply {
            name = "UserTwo"
            contacts = listOf()
        })
        userRepository.save(User().apply {
            name = "deletableUser"
            contacts = listOf()
        })

        val firstNames = listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta")
        val lastNames = listOf("One", "Two", "Three", "Four", "Five", "Six")

        for (i in firstNames.indices) {
            contactRepository.save(Contact().apply {
                firstName = firstNames[i]
                lastName = lastNames[i]
                owner = userOne
            })
        }

        contactRepository.save(Contact().apply {
            firstName = "With"
            lastName = "Number"
            number = "12345"
            owner = userTwo
        })

        contactRepository.save(Contact().apply {
            firstName = "With"
            lastName = "Address"
            owner = userTwo
            address = addressRepository.save(Address().apply {
                street = "SomeStreet 8"
                zip = "12345"
                city = "Dortmund"
                country = "Germany"
            })
        })

        contactRepository.save(Contact().apply {
            firstName = "Without"
            lastName = "Address"
            owner = userTwo
        })

        contactRepository.save(Contact().apply {
            firstName = "With number"
            lastName = "and address"
            number = "123456789"
            address = addressRepository.save(Address().apply {
                street = "SomeStreet 27"
                zip = "98765"
                city = "Dortmund"
                country = "Germany"
            })
        })

        testEntityRepository.save(TestEntity())
    }

    @RequestMapping("/exit", method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    open fun exit() {
      shutdownComponent.exit()
    }

    @RequestMapping("/forceExit", method = [RequestMethod.POST])
    fun forceExit() {
        System.exit(0)
    }
}
