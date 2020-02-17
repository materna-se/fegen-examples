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
    @OneToOne
    lateinit var oneToOneRequired: User

    @ManyToOne(optional = false)
    lateinit var manyToOneRequired: User

    @Nullable
    @OneToOne
    var oneToOneOptional: User? = null

    @Nullable
    @ManyToOne
    var manyToOneOptional: User? = null

    @OneToMany
    var oneToMany: List<User> = listOf()

    @ManyToMany
    var manyToMany: List<User> = listOf()

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
    }
}