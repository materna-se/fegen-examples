package de.materna.fegen.example.maven.kotlin.api

import java.time.*
import java.util.UUID
import java.math.BigDecimal
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import de.materna.fegen.runtime.*

/**
 * This type is used as a basis for the different variants of this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
data class UserBase(

    override val id: Long? = -1L,
    val street: String = "",

    override val _links: UserLinks? = null
): ApiBase<User, UserDto> {

    data class Builder(
        private var id: Long? = -1L,
        private var street: String = "",
        private var _links: UserLinks? = null
    ) {

        constructor(base: UserBase): this() {
            this.id = base.id
            this.street = base.street
            this._links = base._links
        }

        fun id(id: Long?) = apply { this.id = id }
        fun street(street: String) = apply { this.street = street }
        fun _links(_links: UserLinks) = apply { this._links = _links }
        fun build() = UserBase(id, street, _links)
    }

    fun toBuilder() = Builder(this)

    companion object {
        @JvmStatic fun builder() = Builder()
    }

    /**
     * Create a DTO from a base value
     */
    fun toDto(_links: UserLinks) = UserDto(
        id = id, 
        street = street,
        _links = _links
    )
    
    /**
     * A convenience method for the creation of a dto from a base value for testing.
     * Don't use this method in production code.
     */
    fun toDto(id: Long) = toDto(UserLinks(mapOf(
        "self" to ApiNavigationLink("/users/$id", false)
    )))
}

@JsonDeserialize(using = UserLinksDeserializer::class)
data class UserLinks(
    override val linkMap: Map<String, ApiNavigationLink>
): BaseApiNavigationLinks(linkMap) {
    
}

class UserLinksDeserializer(private val vc: Class<*>? = null):  StdDeserializer<UserLinks>(vc) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): UserLinks {
        val jacksonType = ctxt.typeFactory.constructType(object : TypeReference<Map<String, ApiNavigationLink>>() {})
        val deserializer = ctxt.findRootValueDeserializer(jacksonType)
        val map = deserializer.deserialize(p, ctxt)
        return UserLinks::class.java.getConstructor(Map::class.java).newInstance(map)
    }
}


/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
data class UserDto(
    override val id: Long?,
    val street: String,

    override val _links: UserLinks
): ApiDto<User> {

    override fun toObj() = User(
            id = objId, 
            street = street,
            _links = _links
        )
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `UserDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
data class User(
    override val id: Long,
    val street: String,

    override val _links: UserLinks
): ApiObj<UserDto> {
        fun toBuilder() = UserBase.Builder(
            id = id, 
            street = street,
            _links = _links
        )
}
