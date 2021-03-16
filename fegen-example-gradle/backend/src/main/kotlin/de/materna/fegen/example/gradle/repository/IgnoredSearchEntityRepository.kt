package de.materna.fegen.example.gradle.repository

import de.materna.fegen.example.gradle.entity.IgnoredSearchEntity
import de.materna.fegen.util.spring.annotation.FegenIgnore
import org.springframework.data.jpa.repository.JpaRepository

@FegenIgnore
interface IgnoredSearchEntityRepository: JpaRepository<IgnoredSearchEntity, Long> {

    fun findAllByTextIsStartingWith(prefix: String): List<IgnoredSearchEntity>

    @FegenIgnore
    fun findAllByTextIsEndingWith(prefix: String): List<IgnoredSearchEntity>
}