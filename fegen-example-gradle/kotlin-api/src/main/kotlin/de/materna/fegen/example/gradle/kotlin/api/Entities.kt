package de.materna.fegen.example.gradle.kotlin.api

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
data class AddressBase(

    override val id: Long? = -1L,
    val city: String = "",
    val street: String = "",
    val zip: String = "",
    val country: String? = null,
    override val _links: AddressLinks? = null
): ApiBase<Address, AddressDto> {

    data class Builder(
        private var id: Long? = -1L,
        private var city: String = "",
        private var country: String? = null,
        private var street: String = "",
        private var zip: String = "",
        private var _links: AddressLinks? = null
    ) {

        constructor(base: AddressBase): this(
            base.id,
            base.city,
            base.country,
            base.street,
            base.zip,
            base._links
        )

        fun id(id: Long?) = apply { this.id = id }
        fun city(city: String) = apply { this.city = city }
        fun country(country: String?) = apply { this.country = country }
        fun street(street: String) = apply { this.street = street }
        fun zip(zip: String) = apply { this.zip = zip }
        fun _links(_links: AddressLinks) = apply { this._links = _links }
        fun build() = AddressBase(id, city, street, zip, country, _links)
    }

    fun toBuilder() = Builder(this)

    companion object {
        @JvmStatic fun builder() = Builder()
    }

    /**
     * Create a DTO from a base value
     */
    fun toDto(_links: AddressLinks) = AddressDto(
        id = id, 
        city = city, 
        country = country, 
        street = street, 
        zip = zip,
        _links = _links
    )
    
    /**
     * A convenience method for the creation of a dto from a base value for testing.
     * Don't use this method in production code.
     */
    fun toDto(id: Long) = toDto(AddressLinks(mapOf(
        "self" to ApiNavigationLink("/contactAddresses/$id", false)
    )))
}

@JsonDeserialize(using = AddressLinksDeserializer::class)
data class AddressLinks(
    override val linkMap: Map<String, ApiNavigationLink>
): BaseApiNavigationLinks(linkMap) {
    
}

class AddressLinksDeserializer(private val vc: Class<*>? = null):  StdDeserializer<AddressLinks>(vc) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): AddressLinks {
        val jacksonType = ctxt.typeFactory.constructType(object : TypeReference<Map<String, ApiNavigationLink>>() {})
        val deserializer = ctxt.findRootValueDeserializer(jacksonType)
        val map = deserializer.deserialize(p, ctxt)
        return AddressLinks::class.java.getConstructor(Map::class.java).newInstance(map)
    }
}


/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
data class AddressDto(
    override val id: Long?,
    val city: String,
    val country: String?,
    val street: String,
    val zip: String,

    override val _links: AddressLinks
): ApiDto<Address> {

    override fun toObj() = Address(
            id = objId, 
            city = city, 
            country = country, 
            street = street, 
            zip = zip,
            _links = _links
        )
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `AddressDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
data class Address(
    override val id: Long,
    val city: String,
    val country: String?,
    val street: String,
    val zip: String,

    override val _links: AddressLinks
): ApiObj<AddressDto> {
        fun toBuilder(
            
        ) = AddressBase.Builder(
            id = id,
            city = city,
            country = country,
            street = street,
            zip = zip,
            _links = _links
        )
}














 /**
  * This type is used as a basis for the different variants of this domain type. It can be created in the frontend
  * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
  */
 data class ContactBase(

     override val id: Long? = -1L,
     val firstName: String = "",
     val lastName: String = "",
     val address: Address? = null,
     val number: String? = null,
     val owner: User? = null,
     override val _links: ContactLinks? = null
 ): ApiBase<Contact, ContactDto> {

     data class Builder(
         private var id: Long? = -1L,
         private var address: Address? = null,
         private var firstName: String = "",
         private var lastName: String = "",
         private var number: String? = null,
         private var owner: User? = null,
         private var _links: ContactLinks? = null
     ) {

         constructor(base: ContactBase): this(
             base.id,
             base.address,
             base.firstName,
             base.lastName,
             base.number,
             base.owner,
             base._links
         )

         fun id(id: Long?) = apply { this.id = id }
         fun address(address: Address?) = apply { this.address = address }
         fun firstName(firstName: String) = apply { this.firstName = firstName }
         fun lastName(lastName: String) = apply { this.lastName = lastName }
         fun number(number: String?) = apply { this.number = number }
         fun owner(owner: User?) = apply { this.owner = owner }
         fun _links(_links: ContactLinks) = apply { this._links = _links }
         fun build() = ContactBase(id, firstName, lastName, address, number, owner, _links)
     }

     fun toBuilder() = Builder(this)

     companion object {
         @JvmStatic fun builder() = Builder()
     }

     /**
      * Create a DTO from a base value
      */
     fun toDto(_links: ContactLinks) = ContactDto(
         id = id, 
         firstName = firstName, 
         lastName = lastName, 
         number = number,
         _links = _links
     )
     
     /**
      * A convenience method for the creation of a dto from a base value for testing.
      * Don't use this method in production code.
      */
     fun toDto(id: Long) = toDto(ContactLinks(mapOf(
         "self" to ApiNavigationLink("/contacts/$id", false),
"address" to ApiNavigationLink("/contacts/$id/address", false),
         "owner" to ApiNavigationLink("/contacts/$id/owner", false)
     )))
 }

 @JsonDeserialize(using = ContactLinksDeserializer::class)
 data class ContactLinks(
     override val linkMap: Map<String, ApiNavigationLink>
 ): BaseApiNavigationLinks(linkMap) {
     val address: ApiNavigationLink by linkMap
     val owner: ApiNavigationLink by linkMap
 }

 class ContactLinksDeserializer(private val vc: Class<*>? = null):  StdDeserializer<ContactLinks>(vc) {
     override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ContactLinks {
         val jacksonType = ctxt.typeFactory.constructType(object : TypeReference<Map<String, ApiNavigationLink>>() {})
         val deserializer = ctxt.findRootValueDeserializer(jacksonType)
         val map = deserializer.deserialize(p, ctxt)
         return ContactLinks::class.java.getConstructor(Map::class.java).newInstance(map)
     }
 }


 /**
  * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
  * this type will be returned.
  */
 data class ContactDto(
     override val id: Long?,
     val firstName: String,
     val lastName: String,
     val number: String?,

     override val _links: ContactLinks
 ): ApiDto<Contact> {

     override fun toObj() = Contact(
             id = objId, 
             firstName = firstName, 
             lastName = lastName, 
             number = number,
             _links = _links
         )
 }

 /**
  * This type is the default type of choice in the frontend as it has an id (which can be added to the `ContactDto`
  * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
  */
 data class Contact(
     override val id: Long,
     val firstName: String,
     val lastName: String,
     val number: String?,

     override val _links: ContactLinks
 ): ApiObj<ContactDto> {
         fun toBuilder(
             address: Address? = null,
             owner: User? = null
         ) = ContactBase.Builder(
             id = id,
             address = address,
             firstName = firstName,
             lastName = lastName,
             number = number,
             owner = owner,
             _links = _links
         )
 }

data class ContactFullDto(
    override val id: Long?,
    val firstName: String,
    val lastName: String,
    val number: String?,
    val address: AddressDto?,

    override val _links: ContactLinks
): ApiDto<ContactFull> {

    override fun toObj() = ContactFull(
            id = objId, 
            firstName = firstName, 
            lastName = lastName, 
            number = number,
            address = address?.toObj(),
            _links = _links
        )
}

data class ContactFull(
    override val id: Long,
    val firstName: String,
    val lastName: String,
    val number: String?,
    val address: Address?,

    override val _links: ContactLinks
): ApiProjection<ContactFullDto, Contact> {

    override fun toObj() = Contact(
            id = id, 
            firstName = firstName, 
            lastName = lastName, 
            number = number,
            _links = _links
        )
}

data class EmbeddableTestEntity (
    val embeddedLong: Long = 0L,
    val embeddedNullableInt: Int? = 0,
    val embeddedText: String = ""
)

data class FullRelTestEntityDto(
    override val id: Long?,
    val testString: String,
    val embedded: EmbeddableTestEntity?,
    val embeddedNullable: OtherEmbeddableTestEntity?,
    val manyToMany: List<UserDto>,
    val manyToOneOptional: UserDto?,
    val manyToOneRequired: UserDto,
    val oneToMany: List<UserDto>,
    val oneToOneOptional: UserDto?,
    val oneToOneRequired: UserDto,

    override val _links: RelTestEntityLinks
): ApiDto<FullRelTestEntity> {

    override fun toObj() = FullRelTestEntity(
            id = objId, 
            testString = testString, 
            embedded = embedded, 
            embeddedNullable = embeddedNullable,
            manyToMany = manyToMany.map { it.toObj() }, 
            manyToOneOptional = manyToOneOptional?.toObj(), 
            manyToOneRequired = manyToOneRequired.toObj(), 
            oneToMany = oneToMany.map { it.toObj() }, 
            oneToOneOptional = oneToOneOptional?.toObj(), 
            oneToOneRequired = oneToOneRequired.toObj(),
            _links = _links
        )
}

data class FullRelTestEntity(
    override val id: Long,
    val testString: String,
    val embedded: EmbeddableTestEntity?,
    val embeddedNullable: OtherEmbeddableTestEntity?,
    val manyToMany: List<User>,
    val manyToOneOptional: User?,
    val manyToOneRequired: User,
    val oneToMany: List<User>,
    val oneToOneOptional: User?,
    val oneToOneRequired: User,

    override val _links: RelTestEntityLinks
): ApiProjection<FullRelTestEntityDto, RelTestEntity> {

    override fun toObj() = RelTestEntity(
            id = id, 
            testString = testString, 
            embedded = embedded, 
            embeddedNullable = embeddedNullable,
            _links = _links
        )
}


/**
 * This type is used as a basis for the different variants of this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
data class NotExportedTestEntityBase(

    override val id: Long? = -1L,
    val text: String = "",
    override val _links: NotExportedTestEntityLinks? = null
): ApiBase<NotExportedTestEntity, NotExportedTestEntityDto> {

    data class Builder(
        private var id: Long? = -1L,
        private var text: String = "",
        private var _links: NotExportedTestEntityLinks? = null
    ) {

        constructor(base: NotExportedTestEntityBase): this(
            base.id,
            base.text,
            base._links
        )

        fun id(id: Long?) = apply { this.id = id }
        fun text(text: String) = apply { this.text = text }
        fun _links(_links: NotExportedTestEntityLinks) = apply { this._links = _links }
        fun build() = NotExportedTestEntityBase(id, text, _links)
    }

    fun toBuilder() = Builder(this)

    companion object {
        @JvmStatic fun builder() = Builder()
    }

    /**
     * Create a DTO from a base value
     */
    fun toDto(_links: NotExportedTestEntityLinks) = NotExportedTestEntityDto(
        id = id, 
        text = text,
        _links = _links
    )
    
    /**
     * A convenience method for the creation of a dto from a base value for testing.
     * Don't use this method in production code.
     */
    fun toDto(id: Long) = toDto(NotExportedTestEntityLinks(mapOf(
        "self" to ApiNavigationLink("/notExportedTestEntities/$id", false)
    )))
}

@JsonDeserialize(using = NotExportedTestEntityLinksDeserializer::class)
data class NotExportedTestEntityLinks(
    override val linkMap: Map<String, ApiNavigationLink>
): BaseApiNavigationLinks(linkMap) {
    
}

class NotExportedTestEntityLinksDeserializer(private val vc: Class<*>? = null):  StdDeserializer<NotExportedTestEntityLinks>(vc) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): NotExportedTestEntityLinks {
        val jacksonType = ctxt.typeFactory.constructType(object : TypeReference<Map<String, ApiNavigationLink>>() {})
        val deserializer = ctxt.findRootValueDeserializer(jacksonType)
        val map = deserializer.deserialize(p, ctxt)
        return NotExportedTestEntityLinks::class.java.getConstructor(Map::class.java).newInstance(map)
    }
}


/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
data class NotExportedTestEntityDto(
    override val id: Long?,
    val text: String,

    override val _links: NotExportedTestEntityLinks
): ApiDto<NotExportedTestEntity> {

    override fun toObj() = NotExportedTestEntity(
            id = objId, 
            text = text,
            _links = _links
        )
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `NotExportedTestEntityDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
data class NotExportedTestEntity(
    override val id: Long,
    val text: String,

    override val _links: NotExportedTestEntityLinks
): ApiObj<NotExportedTestEntityDto> {
        fun toBuilder(
            
        ) = NotExportedTestEntityBase.Builder(
            id = id,
            text = text,
            _links = _links
        )
}

data class OtherEmbeddableTestEntity (
    val embeddedNullableText: String? = "",
    val otherEmbeddedNullableInt: Int? = 0
)


/**
 * This type is used as a basis for the different variants of this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
data class PrimitiveTestEntityBase(

    override val id: Long? = -1L,
    val booleanTrue: Boolean = false,
    val date2000_6_12: LocalDate = LocalDate.parse("1970-01-01"),
    val int32: Int = 0,
    val intMinusBillion: Int = 0,
    val long64: Long = 0L,
    val stringText: String = "",
    val dateTime2000_1_1_12_30: LocalDateTime? = null,
    val optionalIntBillion: Int? = null,
    val optionalIntNull: Int? = null,
    override val _links: PrimitiveTestEntityLinks? = null
): ApiBase<PrimitiveTestEntity, PrimitiveTestEntityDto> {

    data class Builder(
        private var id: Long? = -1L,
        private var booleanTrue: Boolean = false,
        private var date2000_6_12: LocalDate = LocalDate.parse("1970-01-01"),
        private var dateTime2000_1_1_12_30: LocalDateTime? = null,
        private var int32: Int = 0,
        private var intMinusBillion: Int = 0,
        private var long64: Long = 0L,
        private var optionalIntBillion: Int? = null,
        private var optionalIntNull: Int? = null,
        private var stringText: String = "",
        private var _links: PrimitiveTestEntityLinks? = null
    ) {

        constructor(base: PrimitiveTestEntityBase): this(
            base.id,
            base.booleanTrue,
            base.date2000_6_12,
            base.dateTime2000_1_1_12_30,
            base.int32,
            base.intMinusBillion,
            base.long64,
            base.optionalIntBillion,
            base.optionalIntNull,
            base.stringText,
            base._links
        )

        fun id(id: Long?) = apply { this.id = id }
        fun booleanTrue(booleanTrue: Boolean) = apply { this.booleanTrue = booleanTrue }
        fun date2000_6_12(date2000_6_12: LocalDate) = apply { this.date2000_6_12 = date2000_6_12 }
        fun dateTime2000_1_1_12_30(dateTime2000_1_1_12_30: LocalDateTime?) = apply { this.dateTime2000_1_1_12_30 = dateTime2000_1_1_12_30 }
        fun int32(int32: Int) = apply { this.int32 = int32 }
        fun intMinusBillion(intMinusBillion: Int) = apply { this.intMinusBillion = intMinusBillion }
        fun long64(long64: Long) = apply { this.long64 = long64 }
        fun optionalIntBillion(optionalIntBillion: Int?) = apply { this.optionalIntBillion = optionalIntBillion }
        fun optionalIntNull(optionalIntNull: Int?) = apply { this.optionalIntNull = optionalIntNull }
        fun stringText(stringText: String) = apply { this.stringText = stringText }
        fun _links(_links: PrimitiveTestEntityLinks) = apply { this._links = _links }
        fun build() = PrimitiveTestEntityBase(id, booleanTrue, date2000_6_12, int32, intMinusBillion, long64, stringText, dateTime2000_1_1_12_30, optionalIntBillion, optionalIntNull, _links)
    }

    fun toBuilder() = Builder(this)

    companion object {
        @JvmStatic fun builder() = Builder()
    }

    /**
     * Create a DTO from a base value
     */
    fun toDto(_links: PrimitiveTestEntityLinks) = PrimitiveTestEntityDto(
        id = id, 
        booleanTrue = booleanTrue, 
        date2000_6_12 = date2000_6_12, 
        dateTime2000_1_1_12_30 = dateTime2000_1_1_12_30, 
        int32 = int32, 
        intMinusBillion = intMinusBillion, 
        long64 = long64, 
        optionalIntBillion = optionalIntBillion, 
        optionalIntNull = optionalIntNull, 
        stringText = stringText,
        _links = _links
    )
    
    /**
     * A convenience method for the creation of a dto from a base value for testing.
     * Don't use this method in production code.
     */
    fun toDto(id: Long) = toDto(PrimitiveTestEntityLinks(mapOf(
        "self" to ApiNavigationLink("/primitiveTestEntities/$id", false)
    )))
}

@JsonDeserialize(using = PrimitiveTestEntityLinksDeserializer::class)
data class PrimitiveTestEntityLinks(
    override val linkMap: Map<String, ApiNavigationLink>
): BaseApiNavigationLinks(linkMap) {
    
}

class PrimitiveTestEntityLinksDeserializer(private val vc: Class<*>? = null):  StdDeserializer<PrimitiveTestEntityLinks>(vc) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): PrimitiveTestEntityLinks {
        val jacksonType = ctxt.typeFactory.constructType(object : TypeReference<Map<String, ApiNavigationLink>>() {})
        val deserializer = ctxt.findRootValueDeserializer(jacksonType)
        val map = deserializer.deserialize(p, ctxt)
        return PrimitiveTestEntityLinks::class.java.getConstructor(Map::class.java).newInstance(map)
    }
}


/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
data class PrimitiveTestEntityDto(
    override val id: Long?,
    val booleanTrue: Boolean,
    val date2000_6_12: LocalDate,
    val dateTime2000_1_1_12_30: LocalDateTime?,
    val int32: Int,
    val intMinusBillion: Int,
    val long64: Long,
    val optionalIntBillion: Int?,
    val optionalIntNull: Int?,
    val stringText: String,

    override val _links: PrimitiveTestEntityLinks
): ApiDto<PrimitiveTestEntity> {

    override fun toObj() = PrimitiveTestEntity(
            id = objId, 
            booleanTrue = booleanTrue, 
            date2000_6_12 = date2000_6_12, 
            dateTime2000_1_1_12_30 = dateTime2000_1_1_12_30, 
            int32 = int32, 
            intMinusBillion = intMinusBillion, 
            long64 = long64, 
            optionalIntBillion = optionalIntBillion, 
            optionalIntNull = optionalIntNull, 
            stringText = stringText,
            _links = _links
        )
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `PrimitiveTestEntityDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
data class PrimitiveTestEntity(
    override val id: Long,
    val booleanTrue: Boolean,
    val date2000_6_12: LocalDate,
    val dateTime2000_1_1_12_30: LocalDateTime?,
    val int32: Int,
    val intMinusBillion: Int,
    val long64: Long,
    val optionalIntBillion: Int?,
    val optionalIntNull: Int?,
    val stringText: String,

    override val _links: PrimitiveTestEntityLinks
): ApiObj<PrimitiveTestEntityDto> {
        fun toBuilder(
            
        ) = PrimitiveTestEntityBase.Builder(
            id = id,
            booleanTrue = booleanTrue,
            date2000_6_12 = date2000_6_12,
            dateTime2000_1_1_12_30 = dateTime2000_1_1_12_30,
            int32 = int32,
            intMinusBillion = intMinusBillion,
            long64 = long64,
            optionalIntBillion = optionalIntBillion,
            optionalIntNull = optionalIntNull,
            stringText = stringText,
            _links = _links
        )
}


 /**
  * This type is used as a basis for the different variants of this domain type. It can be created in the frontend
  * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
  */
 data class RelTestEntityBase(

     override val id: Long? = -1L,
     val manyToMany: List<User> = listOf(),
     val manyToOneRequired: User,
     val oneToMany: List<User> = listOf(),
     val oneToOneRequired: User,
     val testString: String = "",
     val embedded: EmbeddableTestEntity? = null,
     val embeddedNullable: OtherEmbeddableTestEntity? = null,
     val manyToOneOptional: User? = null,
     val oneToOneOptional: User? = null,
     override val _links: RelTestEntityLinks? = null
 ): ApiBase<RelTestEntity, RelTestEntityDto> {

     data class Builder(
         private var id: Long? = -1L,
         private var embedded: EmbeddableTestEntity? = null,
         private var embeddedNullable: OtherEmbeddableTestEntity? = null,
         private var manyToMany: List<User> = listOf(),
         private var manyToOneOptional: User? = null,
         private var manyToOneRequired: User,
         private var oneToMany: List<User> = listOf(),
         private var oneToOneOptional: User? = null,
         private var oneToOneRequired: User,
         private var testString: String = "",
         private var _links: RelTestEntityLinks? = null
     ) {

         constructor(base: RelTestEntityBase): this(
             base.id,
             base.embedded,
             base.embeddedNullable,
             base.manyToMany,
             base.manyToOneOptional,
             base.manyToOneRequired,
             base.oneToMany,
             base.oneToOneOptional,
             base.oneToOneRequired,
             base.testString,
             base._links
         )

         fun id(id: Long?) = apply { this.id = id }
         fun embedded(embedded: EmbeddableTestEntity?) = apply { this.embedded = embedded }
         fun embeddedNullable(embeddedNullable: OtherEmbeddableTestEntity?) = apply { this.embeddedNullable = embeddedNullable }
         fun manyToMany(manyToMany: List<User>) = apply { this.manyToMany = manyToMany }
         fun manyToOneOptional(manyToOneOptional: User?) = apply { this.manyToOneOptional = manyToOneOptional }
         fun manyToOneRequired(manyToOneRequired: User) = apply { this.manyToOneRequired = manyToOneRequired }
         fun oneToMany(oneToMany: List<User>) = apply { this.oneToMany = oneToMany }
         fun oneToOneOptional(oneToOneOptional: User?) = apply { this.oneToOneOptional = oneToOneOptional }
         fun oneToOneRequired(oneToOneRequired: User) = apply { this.oneToOneRequired = oneToOneRequired }
         fun testString(testString: String) = apply { this.testString = testString }
         fun _links(_links: RelTestEntityLinks) = apply { this._links = _links }
         fun build() = RelTestEntityBase(id, manyToMany, manyToOneRequired, oneToMany, oneToOneRequired, testString, embedded, embeddedNullable, manyToOneOptional, oneToOneOptional, _links)
     }

     fun toBuilder() = Builder(this)

     companion object {
         @JvmStatic fun builder(
             manyToOneRequired: User,
             oneToOneRequired: User
         ) = Builder(
             manyToOneRequired = manyToOneRequired,
             oneToOneRequired = oneToOneRequired
         )
     }

     /**
      * Create a DTO from a base value
      */
     fun toDto(_links: RelTestEntityLinks) = RelTestEntityDto(
         id = id, 
         testString = testString, 
         embedded = embedded, 
         embeddedNullable = embeddedNullable,
         _links = _links
     )
     
     /**
      * A convenience method for the creation of a dto from a base value for testing.
      * Don't use this method in production code.
      */
     fun toDto(id: Long) = toDto(RelTestEntityLinks(mapOf(
         "self" to ApiNavigationLink("/relTestEntities/$id", false),
"manyToMany" to ApiNavigationLink("/relTestEntities/$id/manyToMany", false),
         "manyToOneOptional" to ApiNavigationLink("/relTestEntities/$id/manyToOneOptional", false),
         "manyToOneRequired" to ApiNavigationLink("/relTestEntities/$id/manyToOneRequired", false),
         "oneToMany" to ApiNavigationLink("/relTestEntities/$id/oneToMany", false),
         "oneToOneOptional" to ApiNavigationLink("/relTestEntities/$id/oneToOneOptional", false),
         "oneToOneRequired" to ApiNavigationLink("/relTestEntities/$id/oneToOneRequired", false)
     )))
 }

 @JsonDeserialize(using = RelTestEntityLinksDeserializer::class)
 data class RelTestEntityLinks(
     override val linkMap: Map<String, ApiNavigationLink>
 ): BaseApiNavigationLinks(linkMap) {
     val manyToMany: ApiNavigationLink by linkMap
     val manyToOneOptional: ApiNavigationLink by linkMap
     val manyToOneRequired: ApiNavigationLink by linkMap
     val oneToMany: ApiNavigationLink by linkMap
     val oneToOneOptional: ApiNavigationLink by linkMap
     val oneToOneRequired: ApiNavigationLink by linkMap
 }

 class RelTestEntityLinksDeserializer(private val vc: Class<*>? = null):  StdDeserializer<RelTestEntityLinks>(vc) {
     override fun deserialize(p: JsonParser, ctxt: DeserializationContext): RelTestEntityLinks {
         val jacksonType = ctxt.typeFactory.constructType(object : TypeReference<Map<String, ApiNavigationLink>>() {})
         val deserializer = ctxt.findRootValueDeserializer(jacksonType)
         val map = deserializer.deserialize(p, ctxt)
         return RelTestEntityLinks::class.java.getConstructor(Map::class.java).newInstance(map)
     }
 }


 /**
  * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
  * this type will be returned.
  */
 data class RelTestEntityDto(
     override val id: Long?,
     val testString: String,
     val embedded: EmbeddableTestEntity?,
     val embeddedNullable: OtherEmbeddableTestEntity?,

     override val _links: RelTestEntityLinks
 ): ApiDto<RelTestEntity> {

     override fun toObj() = RelTestEntity(
             id = objId, 
             testString = testString, 
             embedded = embedded, 
             embeddedNullable = embeddedNullable,
             _links = _links
         )
 }

 /**
  * This type is the default type of choice in the frontend as it has an id (which can be added to the `RelTestEntityDto`
  * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
  */
 data class RelTestEntity(
     override val id: Long,
     val testString: String,
     val embedded: EmbeddableTestEntity?,
     val embeddedNullable: OtherEmbeddableTestEntity?,

     override val _links: RelTestEntityLinks
 ): ApiObj<RelTestEntityDto> {
         fun toBuilder(
             manyToMany: List<User> = listOf(),
             manyToOneOptional: User? = null,
             manyToOneRequired: User,
             oneToMany: List<User> = listOf(),
             oneToOneOptional: User? = null,
             oneToOneRequired: User
         ) = RelTestEntityBase.Builder(
             id = id,
             embedded = embedded,
             embeddedNullable = embeddedNullable,
             manyToMany = manyToMany,
             manyToOneOptional = manyToOneOptional,
             manyToOneRequired = manyToOneRequired,
             oneToMany = oneToMany,
             oneToOneOptional = oneToOneOptional,
             oneToOneRequired = oneToOneRequired,
             testString = testString,
             _links = _links
         )
 }


 /**
  * This type is used as a basis for the different variants of this domain type. It can be created in the frontend
  * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
  */
 data class UserBase(

     override val id: Long? = -1L,
     val contacts: List<Contact> = listOf(),
     val name: String = "",
     override val _links: UserLinks? = null
 ): ApiBase<User, UserDto> {

     data class Builder(
         private var id: Long? = -1L,
         private var contacts: List<Contact> = listOf(),
         private var name: String = "",
         private var _links: UserLinks? = null
     ) {

         constructor(base: UserBase): this(
             base.id,
             base.contacts,
             base.name,
             base._links
         )

         fun id(id: Long?) = apply { this.id = id }
         fun contacts(contacts: List<Contact>) = apply { this.contacts = contacts }
         fun name(name: String) = apply { this.name = name }
         fun _links(_links: UserLinks) = apply { this._links = _links }
         fun build() = UserBase(id, contacts, name, _links)
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
         name = name,
         _links = _links
     )
     
     /**
      * A convenience method for the creation of a dto from a base value for testing.
      * Don't use this method in production code.
      */
     fun toDto(id: Long) = toDto(UserLinks(mapOf(
         "self" to ApiNavigationLink("/users/$id", false),
"contacts" to ApiNavigationLink("/users/$id/contacts", false)
     )))
 }

 @JsonDeserialize(using = UserLinksDeserializer::class)
 data class UserLinks(
     override val linkMap: Map<String, ApiNavigationLink>
 ): BaseApiNavigationLinks(linkMap) {
     val contacts: ApiNavigationLink by linkMap
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
     val name: String,

     override val _links: UserLinks
 ): ApiDto<User> {

     override fun toObj() = User(
             id = objId, 
             name = name,
             _links = _links
         )
 }

 /**
  * This type is the default type of choice in the frontend as it has an id (which can be added to the `UserDto`
  * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
  */
 data class User(
     override val id: Long,
     val name: String,

     override val _links: UserLinks
 ): ApiObj<UserDto> {
         fun toBuilder(
             contacts: List<Contact> = listOf()
         ) = UserBase.Builder(
             id = id,
             contacts = contacts,
             name = name,
             _links = _links
         )
 }

data class CreateRequest (
    val city: String,
    val country: String,
    val firstName: String,
    val lastName: String,
    val number: String,
    val street: String,
    val userName: String,
    val zip: String
)

data class ComplexPojoTest (
    val pojos: List<PrimitivePojoTest>
)

data class PrimitivePojoTest (
    val boolean: Boolean?,
    val number: Double?,
    val string: String?
)