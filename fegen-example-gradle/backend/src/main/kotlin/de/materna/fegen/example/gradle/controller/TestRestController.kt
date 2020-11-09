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

import de.materna.fegen.example.gradle.entity.*
import de.materna.fegen.example.gradle.pojo.*
import de.materna.fegen.example.gradle.repository.PrimitiveTestEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/custom/primitiveTestEntities")
open class TestRestController(
        @Autowired private val primitiveTestEntityRepository: PrimitiveTestEntityRepository
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
    ): ResponseEntity<EntityModel<PrimitiveTestEntity>> {
        val savedEntity = primitiveTestEntityRepository.save(PrimitiveTestEntity().also {
            it.int32 = int32
            it.long64 = long64
            it.intMinusBillion = intMinusBillion
            it.stringText = stringText
            it.booleanTrue = booleanTrue
            it.date2000_6_12 = date2000_6_12
        })

        return ResponseEntity.ok(EntityModel(savedEntity))
    }

    @RequestMapping(path = ["requestParamCreate"], method = [RequestMethod.POST])
    @ResponseBody
    fun requestParam(
            @RequestParam int32: Int,
            @RequestParam("long64Custom") long64: Long,
            @RequestParam(required = false) optionalIntNull: Int?,
            @RequestParam(required = false) optionalIntBillion: Int?,
            @RequestParam intMinusBillion: Int,
            @RequestParam stringText: String,
            @RequestParam booleanTrue: Boolean,
            @RequestParam(name = "dateCustom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date2000_6_12: LocalDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dateTime2000_1_1_12_30: LocalDateTime?
    ): ResponseEntity<EntityModel<PrimitiveTestEntity>> {
        val savedEntity = primitiveTestEntityRepository.save(PrimitiveTestEntity().also {
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

        return ResponseEntity.ok(EntityModel(savedEntity))
    }

    @RequestMapping("requestBodyCreate", method = [RequestMethod.POST])
    @ResponseBody
    fun responseBody(
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity>> {
        val savedEntity = primitiveTestEntityRepository.save(primitiveTestEntity)

        return ResponseEntity.ok(EntityModel(savedEntity))
    }

    @RequestMapping("mixedCreate/{int32}", method = [RequestMethod.POST])
    @ResponseBody
    fun mixed(
            @PathVariable int32: Int,
            @RequestParam long64: Long,
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity>> {
        val savedEntity = primitiveTestEntityRepository.save(primitiveTestEntity.also {
            it.int32 = int32
            it.long64 = long64
        })

        return ResponseEntity.ok(EntityModel(savedEntity))
    }

    @RequestMapping("noBodyCreate/{int32}", method = [RequestMethod.POST])
    @ResponseBody
    fun noBody(
            @PathVariable int32: Int,
            @RequestParam long64: Long
    ): ResponseEntity<EntityModel<PrimitiveTestEntity>> {
        val savedEntity = primitiveTestEntityRepository.save(PrimitiveTestEntity().also {
            it.int32 = int32
            it.long64 = long64
        })

        return ResponseEntity.ok(EntityModel(savedEntity))
    }

    @RequestMapping("noRequestParamCreate/{int32}", method = [RequestMethod.POST])
    @ResponseBody
    fun noRequestParam(
            @PathVariable int32: Int,
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity>> {
        val savedEntity = primitiveTestEntityRepository.save(primitiveTestEntity.also {
            it.int32 = int32
        })

        return ResponseEntity.ok(EntityModel(savedEntity))
    }

    @RequestMapping("noPathVariableCreate", method = [RequestMethod.POST])
    @ResponseBody
    fun noPathVariable(
            @RequestParam long64: Long,
            @RequestBody primitiveTestEntity: PrimitiveTestEntity
    ): ResponseEntity<EntityModel<PrimitiveTestEntity>> {
        val savedEntity = primitiveTestEntityRepository.save(primitiveTestEntity.also {
            it.long64 = long64
        })

        return ResponseEntity.ok(EntityModel(savedEntity))
    }

    @GetMapping("returnList")
    @ResponseBody
    fun returnList(): ResponseEntity<CollectionModel<PrimitiveTestEntity>> =
            ResponseEntity.ok(CollectionModel(primitiveTestEntityRepository.findAll()))

    @GetMapping("returnVoid")
    fun returnVoid() {
    }

    @GetMapping("returnPaged")
    @ResponseBody
    fun returnPaged(
            pageable: Pageable
    ): ResponseEntity<PagedModel<PrimitiveTestEntity>> {
        val result = primitiveTestEntityRepository.findAll(pageable)
        val pageMetadata = PagedModel.PageMetadata(
                result.size.toLong(),
                result.number.toLong(),
                result.totalElements,
                result.totalPages.toLong()
        )
        return ResponseEntity.ok(PagedModel(result.content, pageMetadata))
    }

    @RequestMapping("pojosAsReturnValue", method = [RequestMethod.GET])
    @ResponseBody
    fun pojosAsReturnValue(): ResponseEntity<List<PrimitiveTestPojo>> {
        val testList = listOf(PrimitiveTestPojo("test1", 12.2, true), PrimitiveTestPojo("test2", 13.3, false))
        return ResponseEntity.ok(testList)
    }

    @RequestMapping("pojoAsBodyAndSingleReturnValue", method = [RequestMethod.POST])
    @ResponseBody
    fun pojoAsBodyAndReturnValue(@RequestBody body: ComplexTestPojo): ResponseEntity<ComplexTestPojo> {
        println("Pojo $body")
        return ResponseEntity.ok(body)
    }

    @RequestMapping("pojoAsBodyAndListReturnValue", method = [RequestMethod.POST])
    @ResponseBody
    fun pojoAsBodyAndListReturnValue(@RequestBody body: ComplexTestPojo): ResponseEntity<List<ComplexTestPojo>> {
        println("Pojo $body")
        val testList = listOf(ComplexTestPojo(listOf(PrimitiveTestPojo("test2", 13.3, false))), body)
        return ResponseEntity.ok(testList)
    }

    @RequestMapping("pojoListAsBody", method = [RequestMethod.POST])
    @ResponseBody
    fun pojoListAsBody(@RequestBody body: List<PrimitiveTestPojo>): ResponseEntity<List<PrimitiveTestPojo>> {
        println("Pojo $body")
        return ResponseEntity.ok(body)
    }

    @RequestMapping("recursivePojo", method = [RequestMethod.POST])
    @ResponseBody
    fun recursivePojo(@RequestBody input: RecursiveTestPojo): ResponseEntity<RecursiveTestPojo> {
        return ResponseEntity.ok(
                RecursiveTestPojo(
                        recursive = input
                )
        )
    }

    @RequestMapping("cyclicPojo", method = [RequestMethod.POST])
    @ResponseBody
    fun cyclicPojo(@RequestBody input: CyclicTestPojoA): ResponseEntity<CyclicTestPojoA> {
        return ResponseEntity.ok(
                CyclicTestPojoA(
                        CyclicTestPojoB(
                                input
                        )
                )
        )
    }
}