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
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

class PojoTest : ApiSpec() {

    init {
        "custom endpoint with pojo as body" {
            val result = apiClient().customEndpointControllerClient.createContact(CreateRequest(
                    userName = "UserOne",
                    firstName = "firstName",
                    lastName = "lastName",
                    number = "",
                    street = "street",
                    zip = "12345",
                    city =  "city",
                    country = "country")
            )

            result shouldNotBe null
            val owner = apiClient().contactClient.readOwner(result)
            owner shouldNotBe null
            owner!!.name shouldBe "UserOne"
            result.firstName shouldBe "firstName"
            result.lastName shouldBe "lastName"
            result.number shouldBe null
            val address = apiClient().contactClient.readAddress(result)
            address shouldNotBe null
            address!!.street shouldBe "street"
            address.zip shouldBe "12345"
            address.city shouldBe "city"
            address.country shouldBe "country"
        }

        "pojo list as return value" {
            val result = apiClient().testRestControllerClient.pojosAsReturnValue()
            result shouldNotBe null
            result.size shouldBe 2
        }

        "pojo as body and single return value" {
            val result = apiClient().testRestControllerClient.pojoAsBodyAndReturnValue(ComplexTestPojo(listOf(PrimitiveTestPojo(true, 42.0, "test"))))
            result shouldNotBe null
            result.pojos.size shouldBe 1
        }

        "pojo as body and list return value" {
            val body = ComplexTestPojo(listOf(PrimitiveTestPojo(true, 42.0, "test")))
            val result = apiClient().testRestControllerClient.pojoAsBodyAndListReturnValue(body)
            result shouldNotBe null
            result.size shouldBe 2
        }

        "pojo list as body" {
            val body = listOf(PrimitiveTestPojo(true, 42.0, "test"))
            val result = apiClient().testRestControllerClient.pojoListAsBody(body)
            result shouldNotBe null
            result.size shouldBe 1
        }

        "recursive pojo as body and return type" {
            val body = RecursiveTestPojo(null)
            val result = apiClient().testRestControllerClient.recursivePojo(body)
            result shouldBe RecursiveTestPojo(RecursiveTestPojo(null))
        }

        "cyclic pojo as body and return type" {
            val body = CyclicTestPojoA(CyclicTestPojoB(null))
            val result = apiClient().testRestControllerClient.cyclicPojo(body)
            result shouldBe CyclicTestPojoA(CyclicTestPojoB(body))
        }
    }
}