package de.materna.fegen.example.gradle.kotlin.api.controller

import com.fasterxml.jackson.core.type.TypeReference
import de.materna.fegen.example.gradle.kotlin.api.ComplexTestPojo
import de.materna.fegen.example.gradle.kotlin.api.CyclicTestPojoA
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntity
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntityBase
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestEntityDto
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestPojo
import de.materna.fegen.example.gradle.kotlin.api.RecursiveTestPojo
import de.materna.fegen.runtime.ApiHateoasList
import de.materna.fegen.runtime.ApiHateoasPage
import de.materna.fegen.runtime.PagedItems
import de.materna.fegen.runtime.RequestAdapter
import de.materna.fegen.runtime.appendParams
import de.materna.fegen.runtime.isEndpointCallAllowed
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
    suspend fun cyclicPojo(body: CyclicTestPojoA): CyclicTestPojoA {
        val url = """/api/custom/testController/cyclicPojo""".appendParams()
        return requestAdapter.doSingleRequestWithoutReturnValueTransformation<CyclicTestPojoA,
                CyclicTestPojoA>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isCyclicPojoAllowed(): Boolean {
        val url = """/api/custom/testController/cyclicPojo"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun mixed(
        body: PrimitiveTestEntityBase,
        int32: Int,
        long64: Long
    ): PrimitiveTestEntity {
        val url = """/api/custom/testController/mixedCreate/$int32""".appendParams(
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
    suspend fun isMixedAllowed(int32: Int): Boolean {
        val url = """/api/custom/testController/mixedCreate/$int32"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun noBody(int32: Int, long64: Long): PrimitiveTestEntity {
        val url = """/api/custom/testController/noBodyCreate/$int32""".appendParams(
            "long64" to long64
        )
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
            url = url,
            method = "POST",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isNoBodyAllowed(int32: Int): Boolean {
        val url = """/api/custom/testController/noBodyCreate/$int32"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun noPathVariable(body: PrimitiveTestEntityBase, long64: Long): PrimitiveTestEntity {
        val url = """/api/custom/testController/noPathVariableCreate""".appendParams(
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
    suspend fun isNoPathVariableAllowed(): Boolean {
        val url = """/api/custom/testController/noPathVariableCreate"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun noRequestParam(body: PrimitiveTestEntityBase, int32: Int): PrimitiveTestEntity {
        val url = """/api/custom/testController/noRequestParamCreate/$int32""".appendParams()
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto,
                PrimitiveTestEntityBase>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isNoRequestParamAllowed(int32: Int): Boolean {
        val url = """/api/custom/testController/noRequestParamCreate/$int32"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
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
                """/api/custom/testController/pathVariableCreate/$int32/$long64Custom/$intMinusBillion/$stringText/$booleanTrue/$dateCustom""".appendParams()
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
            url = url,
            method = "POST",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isPathVariableAllowed(
        int32: Int,
        long64Custom: Long,
        intMinusBillion: Int,
        stringText: String,
        booleanTrue: Boolean,
        dateCustom: LocalDate
    ): Boolean {
        val url =
                """/api/custom/testController/pathVariableCreate/$int32/$long64Custom/$intMinusBillion/$stringText/$booleanTrue/$dateCustom"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun pojoAsBodyAndListReturnValue(body: ComplexTestPojo): List<ComplexTestPojo> {
        val url = """/api/custom/testController/pojoAsBodyAndListReturnValue""".appendParams()
        return requestAdapter.doListRequestSimple(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isPojoAsBodyAndListReturnValueAllowed(): Boolean {
        val url = """/api/custom/testController/pojoAsBodyAndListReturnValue"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun pojoAsBodyAndReturnValue(body: ComplexTestPojo): ComplexTestPojo {
        val url = """/api/custom/testController/pojoAsBodyAndSingleReturnValue""".appendParams()
        return requestAdapter.doSingleRequestWithoutReturnValueTransformation<ComplexTestPojo,
                ComplexTestPojo>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isPojoAsBodyAndReturnValueAllowed(): Boolean {
        val url = """/api/custom/testController/pojoAsBodyAndSingleReturnValue"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun pojoListAsBody(body: List<PrimitiveTestPojo>): List<PrimitiveTestPojo> {
        val url = """/api/custom/testController/pojoListAsBody""".appendParams()
        return requestAdapter.doListRequestSimple(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isPojoListAsBodyAllowed(): Boolean {
        val url = """/api/custom/testController/pojoListAsBody"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun pojosAsReturnValue(): List<PrimitiveTestPojo> {
        val url = """/api/custom/testController/pojosAsReturnValue""".appendParams()
        return requestAdapter.doListRequestSimple(
            url = url,
            method = "GET",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isPojosAsReturnValueAllowed(): Boolean {
        val url = """/api/custom/testController/pojosAsReturnValue"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun recursivePojo(body: RecursiveTestPojo): RecursiveTestPojo {
        val url = """/api/custom/testController/recursivePojo""".appendParams()
        return requestAdapter.doSingleRequestWithoutReturnValueTransformation<RecursiveTestPojo,
                RecursiveTestPojo>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isRecursivePojoAllowed(): Boolean {
        val url = """/api/custom/testController/recursivePojo"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
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
        val url = """/api/custom/testController/requestParamCreate""".appendParams(
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
    suspend fun isRequestParamAllowed(): Boolean {
        val url = """/api/custom/testController/requestParamCreate"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun responseBody(body: PrimitiveTestEntityBase): PrimitiveTestEntity {
        val url = """/api/custom/testController/requestBodyCreate""".appendParams()
        return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto,
                PrimitiveTestEntityBase>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isResponseBodyAllowed(): Boolean {
        val url = """/api/custom/testController/requestBodyCreate"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun returnBoolean(): Boolean {
        val url = """/api/custom/testController/returnBoolean""".appendParams()
        return requestAdapter.doSingleRequestWithoutReturnValueTransformation<Boolean>(
            url = url,
            method = "GET",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isReturnBooleanAllowed(): Boolean {
        val url = """/api/custom/testController/returnBoolean"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun returnInteger(): Int {
        val url = """/api/custom/testController/returnInteger""".appendParams()
        return requestAdapter.doSingleRequestWithoutReturnValueTransformation<Int>(
            url = url,
            method = "GET",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isReturnIntegerAllowed(): Boolean {
        val url = """/api/custom/testController/returnInteger"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun returnList(): List<PrimitiveTestEntity> {
        val url = """/api/custom/testController/returnList""".appendParams()
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
    suspend fun isReturnListAllowed(): Boolean {
        val url = """/api/custom/testController/returnList"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun returnPaged(
        page: Int? = null,
        size: Int? = null,
        sort: String? = null
    ): PagedItems<PrimitiveTestEntity> {
        val url = """/api/custom/testController/returnPaged""".appendParams()
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
    suspend fun isReturnPagedAllowed(): Boolean {
        val url = """/api/custom/testController/returnPaged"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun returnString(): String {
        val url = """/api/custom/testController/returnString""".appendParams()
        return requestAdapter.doSingleRequestWithoutReturnValueTransformation<String>(
            url = url,
            method = "GET",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isReturnStringAllowed(): Boolean {
        val url = """/api/custom/testController/returnString"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun returnStringList(): List<String> {
        val url = """/api/custom/testController/returnStringList""".appendParams()
        return requestAdapter.doListRequestSimple(
            url = url,
            method = "GET",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isReturnStringListAllowed(): Boolean {
        val url = """/api/custom/testController/returnStringList"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun returnVoid() {
        val url = """/api/custom/testController/returnVoid""".appendParams()
        return requestAdapter.doVoidRequest(
            url = url,
            method = "GET",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isReturnVoidAllowed(): Boolean {
        val url = """/api/custom/testController/returnVoid"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "GET", url)
    }

    @Suppress("UNUSED")
    suspend fun securedEndpoint(pathParam: String, queryParam: Long): String {
        val url = """/api/custom/testController/securedEndpoint/$pathParam""".appendParams(
            "queryParam" to queryParam
        )
        return requestAdapter.doSingleRequestWithoutReturnValueTransformation<String>(
            url = url,
            method = "PUT",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isSecuredEndpointAllowed(pathParam: String): Boolean {
        val url = """/api/custom/testController/securedEndpoint/$pathParam"""
        return isEndpointCallAllowed(requestAdapter.fetchAdapter, "//api", "PUT", url)
    }
}
