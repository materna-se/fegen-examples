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
import de.materna.fegen.example.gradle.entity.*
import de.materna.fegen.example.gradle.repository.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * This controller is not part of the example, but only used in automated tests
 */
@Controller
@RequestMapping("/api")
open class TestController(
        private val userRepository: UserRepository,
        private val contactRepository: ContactRepository,
        private val addressRepository: AddressRepository,
        private val primitiveTestEntityRepository: PrimitiveTestEntityRepository,
        private val relTestEntityRepository: RelTestEntityRepository,
        private val plainFieldTestEntityRepository: PlainFieldTestEntityRepository,
        private val shutdownComponent: ShutdownComponent
) {

    /**
     * Dummy endpoint that can be used to do basic authentication in tests
     * since it may only be accessed by authenticated users
     */
    @PostMapping("/login")
    @ResponseBody
    fun login() {

    }

    /**
     * Sets the database to a predefined state
     */
    @RequestMapping("/setupTest", method = [RequestMethod.POST])
    @ResponseBody
    @Transactional
    open fun setupTests() {
        relTestEntityRepository.deleteAll()
        addressRepository.deleteAll()
        contactRepository.deleteAll()
        userRepository.deleteAll()
        primitiveTestEntityRepository.deleteAll()

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

        initContacts(userOne, userTwo)

        initPrimitiveTestEntities()

        initRelationTestEntities(userOne, userTwo)

        plainFieldTestEntityRepository.save(PlainFieldTestEntity().apply {
            notNullField = "notNullField"
            bothWithNotNullOnField = "bothWithNotNullOnField"
            bothWithNotNullOnGetter = "bothWithNotNullOnGetter"
            transientFieldWithGetter = "transientFieldWithGetter"
        })
    }

    private fun initContacts(userOne: User, userTwo: User) {
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
    }

    private fun initPrimitiveTestEntities() {
        for (i in 0 until 27) {
            primitiveTestEntityRepository.save(PrimitiveTestEntity().apply {
                int32 = 32 * i
                long64 = 64L * i
                optionalIntNull = null
                optionalIntBillion = 1_000_000_000 * i
                intMinusBillion = -1_000_000_000 * i
                stringText = "abc".repeat(i)
                booleanTrue = i % 2 == 0
                date2000_6_12 = LocalDate.of(2000, 4, i + 1)
                dateTime2000_1_1_12_30 = LocalDateTime.of(2000, 7, i + 1, 0, 2 * i)
            })
        }
    }

    private fun initRelationTestEntities(userOne: User, userTwo: User) {
        saveRelationTestEntity("embeddedIsNull", userOne, userTwo)
        saveRelationTestEntity("embeddedHasNullContent", userOne, userTwo) {
            embeddedNullable = OtherEmbeddableTestEntity()
        }
        saveRelationTestEntity("embeddedHasContent", userOne, userTwo) {
            embeddedNullable = OtherEmbeddableTestEntity().apply {
                embeddedNullableText = "SomeText"
            }
        }
    }

    private fun saveRelationTestEntity(
            text: String,
            userOne: User,
            userTwo: User,
            content: (RelTestEntity.() -> Unit)? = null
    ) {
        RelTestEntity().apply {
            testString = text
            manyToOneRequired = userOne
            oneToOneRequired = userTwo
            embedded = EmbeddableTestEntity()
            if (content != null) {
                content()
            }
            relTestEntityRepository.save(this)
        }
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
