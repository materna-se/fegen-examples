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
package de.materna.fegen.example.gradle.controller

import de.materna.fegen.example.gradle.entity.PrimitiveTestEntity
import de.materna.fegen.example.gradle.repository.TestEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.projection.ProjectionFactory
import org.springframework.data.rest.webmvc.mapping.LinkCollector
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/custom/primitiveTestEntities")
open class TestRestController(
        @Autowired private val testEntityRepository: TestEntityRepository,
        @Autowired private val linkCollector: LinkCollector,
        @Autowired private val projectionFactory: ProjectionFactory
) {

    @RequestMapping("pathVariableCreate/{int32}/{long64Custom}/{intMinusBillion}/{stringText}/{booleanTrue}/{dateCustom}", method = [RequestMethod.POST])
    @ResponseBody
    fun pathVariable(
            @PathVariable int32: Int,
            @PathVariable("long64Custom") long64: Long,
            @PathVariable intMinusBillion: Int,
            @PathVariable stringText: String,
            @PathVariable booleanTrue: Boolean,
            @PathVariable(name = "dateCustom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date2000_6_12: LocalDate
    ): ResponseEntity<EntityModel<PrimitiveTestEntity.BaseProjection>> {
        val savedEntity = testEntityRepository.save(PrimitiveTestEntity().also {
            it.int32 = int32
            it.long64 = long64
            it.intMinusBillion = intMinusBillion
            it.stringText = stringText
            it.booleanTrue = booleanTrue
            it.date2000_6_12 = date2000_6_12
        })

        val projection = projectionFactory.createProjection(PrimitiveTestEntity.BaseProjection::class.java, savedEntity)
        val links = linkCollector.getLinksFor(savedEntity)
        return ResponseEntity.ok(EntityModel(projection, links))
    }

    @RequestMapping(name = "requestParamCreate", method = [RequestMethod.POST])
    @ResponseBody
    fun requestParam(
            @RequestParam int32: Int,
            @RequestParam("long64Custom") long64: Long,
            @RequestParam(required = false) optionalIntNull: Int?,
            @RequestParam(required = false) optionalIntBillion: Int,
            @RequestParam intMinusBillion: Int,
            @RequestParam stringText: String,
            @RequestParam booleanTrue: Boolean,
            @RequestParam(name = "dateCustom") date2000_6_12: LocalDate,
            @RequestParam(required = false) dateTime2000_1_1_12_30: LocalDateTime?
    ): ResponseEntity<EntityModel<PrimitiveTestEntity.BaseProjection>> {
        val savedEntity = testEntityRepository.save(PrimitiveTestEntity().also {
            it.int32 = int32
            it.long64 = long64
            it.optionalIntNull = optionalIntNull
            it.optionalIntBillion = optionalIntBillion
            it.intMinusBillion = intMinusBillion
            it.stringText = stringText
            it.booleanTrue = booleanTrue
            it.date2000_6_12 = date2000_6_12
            it.dateTime2000_1_1_12_30 = dateTime2000_1_1_12_30
        })

        val projection = projectionFactory.createProjection(PrimitiveTestEntity.BaseProjection::class.java, savedEntity)
        val links = linkCollector.getLinksFor(savedEntity)
        return ResponseEntity.ok(EntityModel(projection, links))
    }

    @RequestMapping("requestBodyCreate", method = [RequestMethod.POST])
    @ResponseBody
    fun responseBody(
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity.BaseProjection>> {
        val savedEntity = testEntityRepository.save(primitiveTestEntity)

        val projection = projectionFactory.createProjection(PrimitiveTestEntity.BaseProjection::class.java, savedEntity)
        val links = linkCollector.getLinksFor(savedEntity)
        return ResponseEntity.ok(EntityModel(projection, links))
    }

    @RequestMapping("mixedCreate/{int32}", method = [RequestMethod.POST])
    @ResponseBody
    fun mixed(
            @PathVariable int32: Int,
            @RequestParam long64: Long,
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity.BaseProjection>> {
        val savedEntity = testEntityRepository.save(primitiveTestEntity.also {
            it.int32 = int32
            it.long64 = long64
        })

        val projection = projectionFactory.createProjection(PrimitiveTestEntity.BaseProjection::class.java, savedEntity)
        val links = linkCollector.getLinksFor(savedEntity)
        return ResponseEntity.ok(EntityModel(projection, links))
    }

    @RequestMapping("noBodyCreate/{int32}", method = [RequestMethod.POST])
    @ResponseBody
    fun noBody(
            @PathVariable int32: Int,
            @RequestParam long64: Long
    ): ResponseEntity<EntityModel<PrimitiveTestEntity.BaseProjection>> {
        val savedEntity = testEntityRepository.save(PrimitiveTestEntity().also {
            it.int32 = int32
            it.long64 = long64
        })

        val projection = projectionFactory.createProjection(PrimitiveTestEntity.BaseProjection::class.java, savedEntity)
        val links = linkCollector.getLinksFor(savedEntity)
        return ResponseEntity.ok(EntityModel(projection, links))
    }

    @RequestMapping("noRequestParamCreate/{int32}", method = [RequestMethod.POST])
    @ResponseBody
    fun noRequestParam(
            @PathVariable int32: Int,
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity.BaseProjection>> {
        val savedEntity = testEntityRepository.save(primitiveTestEntity.also {
            it.int32 = int32
        })

        val projection = projectionFactory.createProjection(PrimitiveTestEntity.BaseProjection::class.java, savedEntity)
        val links = linkCollector.getLinksFor(savedEntity)
        return ResponseEntity.ok(EntityModel(projection, links))
    }

    @RequestMapping("noPathVariableCreate", method = [RequestMethod.POST])
    @ResponseBody
    fun noPathVariable(
            @RequestParam long64: Long,
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity.BaseProjection>> {
        val savedEntity = testEntityRepository.save(primitiveTestEntity.also {
            it.long64 = long64
        })

        val projection = projectionFactory.createProjection(PrimitiveTestEntity.BaseProjection::class.java, savedEntity)
        val links = linkCollector.getLinksFor(savedEntity)
        return ResponseEntity.ok(EntityModel(projection, links))
    }
}