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

import de.materna.fegen.runtime.EntitySecurity
import io.kotlintest.shouldBe

class SecurityTest: ApiSpec() {

    init {
        "all permissions for unsecured entity if anonymous" {
            val apiClient = apiClient()
            val allowedMethods = apiClient.primitiveTestEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = true,
                update = true,
                delete = true
            )
        }

        "no permissions for secured entity if anonymous" {
            val apiClient = apiClient()
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = false,
                readAll = false,
                create = false,
                update = false,
                delete = false
            )
        }

        "read permissions for secured entity if reader" {
            val apiClient = login("reader", "pwd")
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = false,
                update = false,
                delete = false
            )
        }

        "create permissions for secured entity if writer" {
            val apiClient = login("writer", "pwd")
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = true,
                update = false,
                delete = false
            )
        }

        "all permissions for secured entity if admin" {
            val apiClient = login("admin", "pwd")
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = true,
                update = true,
                delete = true
            )
        }
    }
}