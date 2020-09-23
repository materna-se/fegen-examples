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
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe

class EmbeddedTest : ApiSpec() {

    init {
        "read including embedded" {
            val relTestEntities = apiClient().relTestEntityRepository.readAll()

            val nullEmbedded = findNamed(relTestEntities.items, "embeddedIsNull")
            val nullContent = findNamed(relTestEntities.items, "embeddedHasNullContent")
            val withContent = findNamed(relTestEntities.items, "embeddedHasContent")

            nullEmbedded.embedded shouldBe EmbeddableTestEntity(
                    8,
                    null,
                    "DefaultText"
            )
            nullEmbedded.embeddedNullable shouldBe null
            nullContent.embeddedNullable shouldBe null
            withContent.embeddedNullable shouldBe OtherEmbeddableTestEntity(
                    embeddedNullableText = "SomeText",
                    otherEmbeddedNullableInt = null
            )
        }

        "creates including embedded" {
            val userOne = fetchUserOne()
            val embedded = EmbeddableTestEntity(
                    embeddedLong = 64,
                    embeddedNullableInt = 32,
                    embeddedText = "created"
            )
            apiClient().relTestEntityRepository.create(RelTestEntityBase(
                    testString = "created",
                    oneToOneRequired = userOne,
                    manyToOneRequired = userOne,
                    embedded = embedded
            ))

            val created = fetchNamed("created")
            created.embedded shouldBe embedded
            created.embeddedNullable shouldBe null
        }

        "updates embedded" {
            val embeddedNullable = OtherEmbeddableTestEntity(
                embeddedNullableText = "NewText",
                otherEmbeddedNullableInt = 42
            )
            val withEmbedded = fetchNamed("embeddedHasContent")
            val modified = withEmbedded.copy(embeddedNullable = embeddedNullable)
            apiClient().relTestEntityClient.update(modified)

            val updated = fetchNamed("embeddedHasContent")
            updated shouldBe modified
        }

        "Updates embedded from null" {
            val embeddedNullable = OtherEmbeddableTestEntity(
                embeddedNullableText = "NewText",
                otherEmbeddedNullableInt = null
            )
            val nullEmbedded = fetchNamed("embeddedIsNull")
            val modified = nullEmbedded.copy(embeddedNullable = embeddedNullable)
            apiClient().relTestEntityClient.update(modified)

            val updated = fetchNamed("embeddedIsNull")
            updated shouldBe modified
        }

        "Updates embedded to null" {
            val withEmbedded = fetchNamed("embeddedHasContent")
            val modified = withEmbedded.copy(embeddedNullable = null)
            apiClient().relTestEntityClient.update(modified)

            val updated = fetchNamed("embeddedHasContent")
            updated shouldBe modified
        }

        "Deletes including embedded"  {
            val withEmbedded = fetchNamed("embeddedHasContent")
            apiClient().relTestEntityClient.delete(withEmbedded)

            val afterDelete = apiClient().relTestEntityClient.readAll()
            afterDelete.items shouldHaveSize  2
        }
    }

    private fun fetchNamed(name: String): RelTestEntity {
        val relTestEntities = apiClient().relTestEntityRepository.readAll()
        return findNamed(relTestEntities.items, name)
    }

    private fun findNamed(items: List<RelTestEntity>, name: String) =
            items.single { it.testString == name }

    private fun fetchUserOne(): User {
        val users = apiClient().userRepository.readAll()
        return users.items.single { it.name == "UserOne" }
    }
}