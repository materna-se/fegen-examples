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

import de.materna.fegen.example.gradle.kotlin.api.UserBase
import io.kotlintest.matchers.collections.shouldNotHaveSize
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe


class CrudTest : ApiSpec() {

    init {
        "read all" {
            val allUsers = apiClient().userRepository.readAll()

            allUsers.items shouldNotHaveSize 0
        }

        "create" {
            val userBase = UserBase(name = "user_name")
            val newUser = apiClient().userRepository.create(userBase)

            newUser shouldNotBe null
            newUser.name shouldBe userBase.name
            newUser.id shouldBeGreaterThanOrEqual 0
            userCount() shouldBe userCountBefore + 1
        }

        "update" {
            val user = apiClient().userRepository.readAll().items[0]
            val changeRequest = user.copy(name = "newName")
            val changedUser = apiClient().userRepository.update(changeRequest)

            changedUser shouldNotBe null
            changedUser.name shouldBe changeRequest.name
            changedUser.id shouldBe changeRequest.id
            userCount() shouldBe userCountBefore
        }

        "delete" {
            val users = apiClient().userRepository.readAll()
            val user = users.items.single { it.name == "deletableUser" }
            apiClient().userRepository.delete(user)

            userCount() shouldBe userCountBefore - 1
        }
    }

    private fun userCount() =
        apiClient().userRepository.readAll().page.totalElements
}