package de.materna.fegen.example.gradle.entity

import org.springframework.lang.Nullable
import javax.persistence.Embeddable

@Embeddable
class OtherEmbeddableTestEntity {

    @Nullable
    var otherEmbeddedNullableInt: Int? = null

    @Nullable
    var embeddedNullableText: String? = null
}