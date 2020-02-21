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

import io.kotlintest.matchers.collections.shouldNotHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import java.time.LocalDate

class CustomEndpointTest : ApiSpec() {

    init {
        "custom endpoint" {
            val result = apiClient().contactRepository.customPostCreateOrUpdate(
                    "UserOne",
                    "firstName",
                    "lastName",
                    "",
                    "street",
                    "12345",
                    "city",
                    "country"
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

        "path variables" {
            val date = LocalDate.of(1234, 7, 13)
            val result = apiClient().primitiveTestEntityClient.customPostPathVariableCreateByInt32ByLong64CustomByIntMinusBillionByStringTextByBooleanTrueByDateCustom(
                    1, 12, -123, "fghtejte", false, date
            )

            result.apply {
                int32 shouldBe 1
                long64 shouldBe 12
                intMinusBillion shouldBe -123
                stringText shouldBe "fghtejte"
                booleanTrue shouldBe false
                date2000_6_12 shouldBe date
            }
        }

        "request parameters" {
            val date = LocalDate.of(2003, 7, 3)
            val result = apiClient().primitiveTestEntityClient.customPostRequestParamCreate(
                    1,
                    12,
                    -123,
                    "grshteh",
                    false,
                    date,
                    -541651664,
                    null,
                    null
            )
            result.apply {
                int32 shouldBe 1
                long64 shouldBe 12
                intMinusBillion shouldBe -123
                stringText shouldBe "grshteh"
                booleanTrue shouldBe false
                date2000_6_12 shouldBe date
                optionalIntNull shouldBe -541651664
                optionalIntBillion shouldBe null
                dateTime2000_1_1_12_30 shouldBe null
            }
        }

        "body" {
            val result = apiClient().primitiveTestEntityClient.customPostRequestBodyCreate(customEntity)

            compareTestEntities(result, customEntity)
        }

        "path variables and request parameters" {
            val result = apiClient().primitiveTestEntityClient.customPostNoBodyCreateByInt32(684, 848)

            result.int32 shouldBe 684
            result.long64 shouldBe 848
        }

        "path variables and request body" {
            val result = apiClient().primitiveTestEntityClient.customPostNoRequestParamCreateByInt32(789, customEntity)

            compareTestEntities(result, customEntity.copy(int32 = 789))
        }

        "request parameters and request body" {
            val result = apiClient().primitiveTestEntityClient.customPostNoPathVariableCreate(customEntity, -65446545)

            compareTestEntities(result, customEntity.copy(long64 = -65446545))
        }

        "return list" {
            val result = apiClient().primitiveTestEntityClient.customGetReturnList()

            result shouldNotHaveSize 0
        }

        "return void" {
            apiClient().primitiveTestEntityClient.customGetReturnVoid()
        }
    }
}