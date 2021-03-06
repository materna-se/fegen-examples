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

import de.materna.fegen.example.gradle.kotlin.api.ContactFull
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

class ProjectionTest: ApiSpec() {

    init {
        "read projection" {
            val contact = fetchWithAddress()

            contact.address shouldNotBe null
            contact.address!!.id shouldBeGreaterThanOrEqual 0
        }

        "fetches single projection" {
            val withAddress = fetchWithAddress()
            val contact = apiClient().contactClient.readOneContactFull(withAddress.id)

            contact.address shouldNotBe null
            contact.address!!.id shouldBeGreaterThanOrEqual 0
        }

        "updates using projections" {
            val withAddress = fetchWithAddress()
            val updated = withAddress.copy(firstName = "NewFirstName")
            val updateResult = apiClient().contactClient.update(updated.toObj())
            val fetchedUpdated = apiClient().contactClient.readOneContactFull(withAddress.id)

            updateResult.id shouldBe updated.id
            updateResult.firstName shouldBe updated.firstName
            updateResult.lastName shouldBe updated.lastName
            updateResult.number shouldBe updated.number

            fetchedUpdated shouldBe updated
        }
    }

    private suspend fun fetchWithAddress(): ContactFull {
        val fullContacts = apiClient().contactClient.readAllContactFull()
        return fullContacts.items.single { it.firstName == "With" && it.lastName == "Address" }
    }
}
