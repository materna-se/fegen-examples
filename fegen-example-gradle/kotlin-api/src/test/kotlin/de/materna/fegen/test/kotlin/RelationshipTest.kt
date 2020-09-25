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
package de.materna.fegen.test.kotlin

import de.materna.fegen.example.gradle.kotlin.api.*
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.collections.shouldNotContain
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

class RelationshipTest : ApiSpec() {

    init {
        "create referencing existing" {
            val user = apiClient().userClient.create(UserBase(
                    name = "CreatedUser"
            ))

            val createdContact = apiClient().contactClient.create(ContactBase(
                    firstName = "Created",
                    lastName = "Contact",
                    address = null,
                    number = "123456789",
                    owner = user
            ))

            val contacts = apiClient().userClient.readContacts(user)

            contacts shouldHaveSize 1
            val contact = contacts.single()
            contact.id shouldBe createdContact.id
            contact.firstName shouldBe "Created"
            contact.lastName shouldBe "Contact"

            val owner = apiClient().contactClient.readOwner(contact)

            owner shouldNotBe null
            owner!!.id shouldBe user.id
            owner.name shouldBe user.name
        }

        "read set of related" {
            val userOne = getUserOne()

            val contacts = apiClient().userClient.readContacts(userOne)

            contacts.size shouldNotBe 0
            contacts.forEach { it.id shouldBeGreaterThanOrEqual 0 }
        }

        "read existing optional related" {
            val contact = getContactWithAddress()

            val address = apiClient().contactClient.readAddress(contact)

            address shouldNotBe null
        }

        "read absent optional related" {
            val contact = getContactWithoutAddress()

            val address = apiClient().contactClient.readAddress(contact)

            address shouldBe null
        }

        "set optional related" {
            val address = createAddress()
            val contact = getContactWithoutAddress()

            apiClient().contactClient.setAddress(contact, address)

            val savedAddress = apiClient().contactClient.readAddress(contact)
            savedAddress shouldNotBe null
            savedAddress!!.id shouldBe address.id
        }

        "replace optional related" {
            val address = createAddress()
            val contact = getContactWithAddress()

            apiClient().contactClient.setAddress(contact, address)

            val savedAddress = apiClient().contactClient.readAddress(contact)
            savedAddress shouldNotBe null
            savedAddress!!.id shouldBe address.id
        }

        "remove relationship" {
            val contact = getContactWithAddress()
            val address = apiClient().contactClient.readAddress(contact)

            apiClient().contactClient.deleteFromAddress(contact, address!!)

            val savedAddess = apiClient().contactClient.readAddress(contact)
            savedAddess shouldBe null
        }

        "add related to set" {
            val contact = ContactBase(
                    firstName = "Example",
                    lastName = "Contact"
            ).let { apiClient().contactClient.create(it) }
            val user = getUserOne()
            val contactsBefore = apiClient().userClient.readContacts(user)

            apiClient().contactClient.setOwner(contact, user)

            val contactsAfter = apiClient().userClient.readContacts(user)
            contactsAfter shouldHaveSize contactsBefore.size + 1
            contactsAfter shouldContain contact
            contactsAfter shouldContainAll contactsBefore
        }

        "remove related from set" {
            val user = getUserOne()
            val contactsBefore = apiClient().userClient.readContacts(user)
            val contactToRemove = contactsBefore.first()

            apiClient().contactClient.deleteFromOwner(contactToRemove, user)

            val contactsAfter = apiClient().userClient.readContacts(user)
            contactsAfter shouldHaveSize contactsBefore.size - 1
            contactsAfter shouldNotContain contactToRemove
            contactsBefore shouldContainAll contactsAfter
        }
    }

    private suspend fun createAddress() = AddressBase(
            street = "ExampleStreet",
            zip = "12345",
            city = "ExampleCity",
            country = "ExampleCountry"
    ).let { apiClient().addressClient.create(it) }

    private suspend fun getAllContacts(): List<Contact> {
        return apiClient().contactClient.readAll(null, null, null).items
    }

    private suspend fun getUserOne(): User {
        val users = apiClient().userClient.readAll(null, null, null)
        return users.items.first { it.name == "UserOne" }
    }

    private suspend fun getContactWithAddress(): Contact {
        return getAllContacts().first { it.firstName == "With" && it.lastName == "Address" }
    }

    private suspend fun getContactWithoutAddress(): Contact {
        return getAllContacts().first { it.firstName == "Without" && it.lastName == "Address" }
    }
}
