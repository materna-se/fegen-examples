package de.materna.fegen.example.gradle.repository

import de.materna.fegen.example.gradle.entity.RelTestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface RelTestEntityRepository: JpaRepository<RelTestEntity, Long>