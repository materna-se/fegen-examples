package de.materna.fegen.example.gradle.entity

import org.springframework.lang.Nullable
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
class EmbeddableTestEntity {

    var embeddedLong: Long = 8

    @Nullable
    var embeddedNullableInt: Int? = null

    @NotNull
    lateinit var embeddedText: String
}