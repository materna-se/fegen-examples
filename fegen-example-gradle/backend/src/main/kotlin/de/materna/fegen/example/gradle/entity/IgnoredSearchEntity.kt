package de.materna.fegen.example.gradle.entity

import org.springframework.data.rest.core.config.Projection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class IgnoredSearchEntity {

    @Id
    @GeneratedValue
    var id: Long = -1

    @NotNull
    lateinit var text: String

    @Projection(name = "baseProjection", types = [IgnoredSearchEntity::class])
    interface BaseProjection {
        val id: Long
        val text: String
    }
}