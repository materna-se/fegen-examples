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
class RelTestEntity {

    @Id
    @GeneratedValue
    var id: Long = -1

    @NotNull
    var testString = ""

    @NotNull
    @OneToOne
    lateinit var oneToOneRequired: User

    @ManyToOne(optional = false)
    lateinit var manyToOneRequired: User

    @Nullable
    @OneToOne
    var oneToOneOptional: User? = null

    @ManyToOne(optional = true)
    var manyToOneOptional: User? = null

    @OneToMany
    var oneToMany: List<User> = listOf()

    @ManyToMany
    var manyToMany: List<User> = listOf()

    @Embedded
    lateinit var embedded: EmbeddableTestEntity

    @Embedded
    var embeddedNullable: OtherEmbeddableTestEntity? = null

    @Projection(name = "baseProjection", types = [RelTestEntity::class])
    interface BaseProjection {
        val id: Long
    }

    @Projection(name = "full", types = [RelTestEntity::class])
    interface FullRelTestEntity {
        val id: Long
        val oneToOneRequired: User.BaseProjection
        val manyToOneRequired: User.BaseProjection
        val oneToOneOptional: User.BaseProjection?
        val manyToOneOptional: User.BaseProjection?
        val oneToMany: List<User.BaseProjection>
        val manyToMany: List<User.BaseProjection>
        val embedded: EmbeddableTestEntity
        val embeddedNullable: OtherEmbeddableTestEntity?
    }
}