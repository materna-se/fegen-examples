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
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Contact {

    @Id
    @GeneratedValue
    var id: Long = -1

    @NotNull
    lateinit var firstName: String

    @NotNull
    lateinit var lastName: String

    @Nullable
    var number: String? = null

    @Nullable
    @OneToOne
    var address: Address? = null

    @Nullable
    @ManyToOne
    lateinit var owner: User

    @Projection(name = "baseProjection", types = [Contact::class])
    interface BaseProjection {
        val id: Long
        val firstName: String
        val lastName: String
        val number: String?
    }

    @Projection(name = "full", types = [Contact::class])
    interface ContactFull {
        val id: Long
        val firstName: String
        val lastName: String
        val number: String?
        val address: Address.BaseProjection?
    }
}
