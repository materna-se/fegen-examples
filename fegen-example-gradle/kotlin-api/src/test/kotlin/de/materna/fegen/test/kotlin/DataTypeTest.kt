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

import de.materna.fegen.example.gradle.kotlin.api.FullRelTestEntity
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.collections.shouldNotHaveSize
import io.kotlintest.shouldNotBe

class DataTypeTest : ApiSpec() {

    init {
        "read datatypes" {
            val entities = apiClient().primitiveTestEntityClient.readAll().items

            entities shouldNotHaveSize 0
        }

        "write datatypes" {
            val createReturn = apiClient().primitiveTestEntityClient.create(customEntity)
            val testEntities = apiClient().primitiveTestEntityClient.readAll(size = Int.MAX_VALUE).items

            createReturn shouldNotBe null
            compareTestEntities(createReturn, customEntity)
            testEntities shouldContain createReturn
        }

        "primitives nullability" {
            // The following fields must be nullable
            val baseEntity = defaultEntity.copy(
                    id = null,
                    dateTime2000_1_1_12_30 = null,
                    optionalIntBillion = null,
                    optionalIntNull = null
            )
            // The following fields must not be nullable
            assertNotNullable(
                    baseEntity.booleanTrue,
                    baseEntity.date2000_6_12,
                    baseEntity.int32,
                    baseEntity.intMinusBillion,
                    baseEntity.long64,
                    baseEntity.stringText
            )

            val entity = defaultEntity.toDto(0).toObj()
            assertNotNullable(entity.id)
        }
    }

    @Suppress("UNUSED")
    private fun testFullRelTestEntityNullability(entity: FullRelTestEntity) {
        entity.copy(
                oneToOneOptional = null,
                manyToOneOptional = null
        )
        assertNotNullable(
                entity.oneToOneRequired,
                entity.manyToOneRequired,
                entity.oneToMany,
                entity.manyToMany
        )
    }

    /**
     * Asserts that the parameter's type is not nullable
     */
    private fun assertNotNullable(@Suppress("UNUSED_PARAMETER") vararg obj: Any) {}
}
