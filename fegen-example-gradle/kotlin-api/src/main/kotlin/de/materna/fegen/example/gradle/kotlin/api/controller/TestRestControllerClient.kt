package de.materna.fegen.example.gradle.kotlin.api.controller

import com.fasterxml.jackson.core.type.TypeReference
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntity
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntityBase
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntityDto
import de.materna.fegen.runtime.ApiHateoasList
import de.materna.fegen.runtime.ApiHateoasPage
import de.materna.fegen.runtime.PagedItems
import de.materna.fegen.runtime.RequestAdapter
import de.materna.fegen.runtime.appendParams
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

class TestRestControllerClient(
    private val requestAdapter: RequestAdapter
) {
    @Suppress("UNUSED")
    suspend fun mixed(
        body: PrimitiveTestEntityBase,
        int32: Int,
        long64: Long
    ): PrimitiveTestEntity {
        val url = """/api/custom/primitiveTestEntities/mixedCreate/$int32""".appendParams(
            "long64" to long64
        )
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto,
                PrimitiveTestEntityBase>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun noBody(int32: Int, long64: Long): PrimitiveTestEntity {
        val url = """/api/custom/primitiveTestEntities/noBodyCreate/$int32""".appendParams(
            "long64" to long64
        )
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
            url = url,
            method = "POST",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun noPathVariable(body: PrimitiveTestEntityBase, long64: Long): PrimitiveTestEntity {
        val url = """/api/custom/primitiveTestEntities/noPathVariableCreate""".appendParams(
            "long64" to long64
        )
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto,
                PrimitiveTestEntityBase>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun noRequestParam(body: PrimitiveTestEntityBase, int32: Int): PrimitiveTestEntity {
        val url = """/api/custom/primitiveTestEntities/noRequestParamCreate/$int32""".appendParams()
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto,
                PrimitiveTestEntityBase>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun pathVariable(
        int32: Int,
        long64Custom: Long,
        intMinusBillion: Int,
        stringText: String,
        booleanTrue: Boolean,
        dateCustom: LocalDate
    ): PrimitiveTestEntity {
        val url =
                """/api/custom/primitiveTestEntities/pathVariableCreate/$int32/$long64Custom/$intMinusBillion/$stringText/$booleanTrue/$dateCustom""".appendParams()
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
            url = url,
            method = "POST",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun requestParam(
        int32: Int,
        long64Custom: Long,
        optionalIntNull: Int?,
        optionalIntBillion: Int?,
        intMinusBillion: Int,
        stringText: String,
        booleanTrue: Boolean,
        dateCustom: LocalDate,
        dateTime2000_1_1_12_30: LocalDateTime?
    ): PrimitiveTestEntity {
        val url = """/api/custom/primitiveTestEntities/requestParamCreate""".appendParams(
            "int32" to int32,
            "long64Custom" to long64Custom,
            "optionalIntNull" to optionalIntNull,
            "optionalIntBillion" to optionalIntBillion,
            "intMinusBillion" to intMinusBillion,
            "stringText" to stringText,
            "booleanTrue" to booleanTrue,
            "dateCustom" to dateCustom,
            "dateTime2000_1_1_12_30" to dateTime2000_1_1_12_30
        )
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
            url = url,
            method = "POST",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun responseBody(body: PrimitiveTestEntityBase): PrimitiveTestEntity {
        val url = """/api/custom/primitiveTestEntities/requestBodyCreate""".appendParams()
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto,
                PrimitiveTestEntityBase>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun returnList(): List<PrimitiveTestEntity> {
        val url = """/api/custom/primitiveTestEntities/returnList""".appendParams()
        return requestAdapter.doListRequest(
            url = url,
            method = "GET",
            embeddedPropName = "primitiveTestEntities",
            ignoreBasePath = true,
            type = object : TypeReference<ApiHateoasList<PrimitiveTestEntityDto,
                PrimitiveTestEntity>>() {}
        )
    }

    @Suppress("UNUSED")
    suspend fun returnPaged(
        page: Int? = null,
        size: Int? = null,
        sort: String? = null
    ): PagedItems<PrimitiveTestEntity> {
        val url = """/api/custom/primitiveTestEntities/returnPaged""".appendParams()
        return requestAdapter.doPageRequest(
            url = url,
            method = "GET",
            embeddedPropName = "primitiveTestEntities",
            page = page,
            size = size,
            sort = sort,
            ignoreBasePath = true,
            type = object : TypeReference<ApiHateoasPage<PrimitiveTestEntityDto,
                PrimitiveTestEntity>>() {}
        )
    }

    @Suppress("UNUSED")
    suspend fun returnVoid() {
        val url = """/api/custom/primitiveTestEntities/returnVoid""".appendParams()
        return requestAdapter.doVoidRequest(
            url = url,
            method = "GET",
            ignoreBasePath = true
        )
    }
}