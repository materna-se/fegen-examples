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
package de.materna.fegen.example.gradle.entity

import org.springframework.data.rest.core.config.Projection
import org.springframework.lang.Nullable
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

/**
 * This entity is not part of the example but only used in automated tests
 * to ensure correct transmission of different data types
 */
@Entity
class PrimitiveTestEntity {

    @Id
    @GeneratedValue
    var id: Long = -1

    @Column(nullable = false)
    var int32: Int = 32

    @NotNull
    var long64: Long = 64

    @Nullable
    var optionalIntNull: Int? = null

    @Nullable
    var optionalIntBillion: Int? = 1_000_000_000

    @NotNull
    var intMinusBillion: Int = -1_000_000_000

    @NotNull
    var stringText: String = "text"

    @NotNull
    var booleanTrue: Boolean = true

    @Column(nullable = false)
    var date2000_6_12: LocalDate = LocalDate.of(2000, 6, 12)

    @Column(nullable = true)
    var dateTime2000_1_1_12_30: LocalDateTime? = LocalDateTime.of(2000, 1, 1, 12, 30)

    @Projection(name = "baseProjection", types = [PrimitiveTestEntity::class])
    interface BaseProjection {
        val id: Long
        val int32: Int
        val long64: Long
        val optionalIntNull: Int?
        val optionalIntBillion: Int?
        val intMinusBillion: Int
        val stringText: String
        val booleanTrue: Boolean
        val date2000_6_12: LocalDate
        val dateTime2000_1_1_12_30: LocalDateTime?
    }
}
