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

import de.materna.fegen.runtime.FetchRequestWrapper
import de.materna.fegen.example.gradle.kotlin.api.ApiClient
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntity
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntityBase
import io.kotlintest.TestCase
import io.kotlintest.provided.SERVER_ADDRESS
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.time.LocalDate
import java.time.LocalDateTime

fun setupTest() {
    val requestBody = RequestBody.create(null, byteArrayOf())
    val request = Request.Builder().run {
        url(SERVER_ADDRESS + "/api/setupTest")
        method("POST", requestBody)
        build()
    }
    OkHttpClient.Builder().build().newCall(request).execute().use { response ->
        if (!response.isSuccessful) {
            throw IllegalStateException("Server could not prepare DB for a test")
        }
    }
}

open class ApiSpec(body: ApiSpec.() -> Unit = {}) : StringSpec() {

    val userCountBefore =
            apiClient().userRepository.readAll(null, null, null).page.totalElements

    val defaultEntity = PrimitiveTestEntityBase(
            booleanTrue = true,
            date2000_6_12 = LocalDate.of(2000, 6, 12),
            dateTime2000_1_1_12_30 = LocalDateTime.of(2000, 1, 1, 12, 30),
            int32 = 32,
            intMinusBillion = -1_000_000_000,
            long64 = 64L,
            optionalIntBillion = 1_000_000_000,
            optionalIntNull = null,
            stringText = "text"
    )

    val customEntity = PrimitiveTestEntityBase(
            booleanTrue = false,
            date2000_6_12 = LocalDate.of(1900, 4, 8),
            dateTime2000_1_1_12_30 = LocalDateTime.of(1950, 12, 31, 23, 59),
            int32 = Int.MAX_VALUE,
            intMinusBillion = 0,
            long64 = Long.MIN_VALUE,
            optionalIntBillion = null,
            optionalIntNull = Int.MIN_VALUE,
            stringText = "27"
    )

    init {
        body()
    }

    fun apiClient() =
            ApiClient(FetchRequestWrapper("http://localhost:8080/"))

    override fun beforeTest(testCase: TestCase) {
        setupTest()
    }

    fun compareTestEntities(actual: PrimitiveTestEntity, expected: PrimitiveTestEntityBase) {
        actual.booleanTrue shouldBe expected.booleanTrue
        actual.date2000_6_12 shouldBe expected.date2000_6_12
        actual.dateTime2000_1_1_12_30 shouldBe expected.dateTime2000_1_1_12_30
        actual.int32 shouldBe expected.int32
        actual.long64 shouldBe expected.long64
        actual.optionalIntBillion shouldBe expected.optionalIntBillion
        actual.optionalIntNull shouldBe expected.optionalIntNull
        actual.stringText shouldBe expected.stringText
    }
}
