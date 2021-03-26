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

import de.materna.fegen.example.gradle.kotlin.api.PlainFieldTestEntity
import io.kotlintest.shouldBe
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class PlainFieldTest : ApiSpec() {

    private val fields = PlainFieldTestEntity::class.memberProperties

    init {
        "notNullField exists as not null" {
            val field = field("notNullField")
            field.returnType.isMarkedNullable shouldBe false
        }

        "nullableField exists as nullable" {
            val field = field("nullableField")
            field.returnType.isMarkedNullable shouldBe true
        }

        "bothWithNotNullOnField exists as not null" {
            val field = field("bothWithNotNullOnField")
            field.returnType.isMarkedNullable shouldBe false
        }

        "bothWithNotNullOnGetter exists as not null" {
            val field = field("bothWithNotNullOnGetter")
            field.returnType.isMarkedNullable shouldBe false
        }

        "transientField does not exist" {
            fields.singleOrNull { it.name == "transientField" } shouldBe null
        }

        "transientFieldWithGetter exists as not null" {
            val field = field("transientFieldWithGetter")
            field.returnType.isMarkedNullable shouldBe false
        }
    }

    private fun field(name: String): KProperty1<PlainFieldTestEntity, *> {
        return fields.single { it.name == name }
    }
}