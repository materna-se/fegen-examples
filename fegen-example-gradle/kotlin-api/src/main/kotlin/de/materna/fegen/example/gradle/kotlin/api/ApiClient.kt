    package de.materna.fegen.example.gradle.kotlin.api

    import java.math.BigDecimal
    import com.fasterxml.jackson.core.type.TypeReference
    import kotlinx.coroutines.runBlocking
    import java.time.*
    import java.util.UUID
    import java.net.URLEncoder
    import de.materna.fegen.runtime.*
    import com.fasterxml.jackson.module.kotlin.registerKotlinModule
    import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.SerializationFeature

    open class ApiClient(val request: FetchRequest) {
        val adapter: RequestAdapter
        
        init {
            request.mapper.registerModule(JavaTimeModule())
            request.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            request.mapper.registerKotlinModule()
            adapter = RequestAdapter(request)
        }
    
        open val addressClient by lazy { AddressClient(apiClient = this, requestAdapter = adapter) }
        open val addressRepository by lazy { AddressRepository(client = addressClient) }
        open val contactClient by lazy { ContactClient(apiClient = this, requestAdapter = adapter) }
        open val contactRepository by lazy { ContactRepository(client = contactClient) }
        open val primitiveTestEntityClient by lazy { PrimitiveTestEntityClient(apiClient = this, requestAdapter = adapter) }
        open val primitiveTestEntityRepository by lazy { PrimitiveTestEntityRepository(client = primitiveTestEntityClient) }
        open val relTestEntityClient by lazy { RelTestEntityClient(apiClient = this, requestAdapter = adapter) }
        open val relTestEntityRepository by lazy { RelTestEntityRepository(client = relTestEntityClient) }
        open val userClient by lazy { UserClient(apiClient = this, requestAdapter = adapter) }
        open val userRepository by lazy { UserRepository(client = userClient) }
    }

    open class AddressClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: AddressBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "/contactAddresses"
        )
    
        @Deprecated(message = "from now on an empty constructor is available in base types (as well as a builder)")
        fun build() = AddressBase(
                id = -1L,
                city = "",
                country = "",
                street = "",
                zip = "",
                _links = null
            )
    
        suspend fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<Address, AddressDto>(
                projectionName = null,
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<AddressDto, Address>>() {}
            )
    
        
    
        private suspend inline fun <reified T: ApiObj<U>, reified U: ApiDto<T>> readProjections(
                projectionName: String?, page: Int?, size: Int?, sort: String?,
                type: TypeReference<ApiHateoasPage<U, T>>
        ) =
            requestAdapter.doPageRequest<T, U>(
                url = "/contactAddresses",
                embeddedPropName = "contactAddresses",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<Address, AddressDto>(
            id = id,
            uri = "/contactAddresses"
        )
    
    
        
    
        suspend fun update(obj: Address) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: Address) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "/contactAddresses")
    
    
        
    
        
    
        
    
    }
    
    open class ContactClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: ContactBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "/contacts"
        )
    
        @Deprecated(message = "from now on an empty constructor is available in base types (as well as a builder)")
        fun build() = ContactBase(
                id = -1L,
                firstName = "",
                lastName = "",
                number = "",
                _links = null
            )
    
        suspend fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<Contact, ContactDto>(
                projectionName = null,
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<ContactDto, Contact>>() {}
            )
    
        suspend fun readAllContactFull(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<ContactFull, ContactFullDto>(
                projectionName = "full",
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<ContactFullDto, ContactFull>>() {}
            )
    
        private suspend inline fun <reified T: ApiObj<U>, reified U: ApiDto<T>> readProjections(
                projectionName: String?, page: Int?, size: Int?, sort: String?,
                type: TypeReference<ApiHateoasPage<U, T>>
        ) =
            requestAdapter.doPageRequest<T, U>(
                url = "/contacts",
                embeddedPropName = "contacts",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<Contact, ContactDto>(
            id = id,
            uri = "/contacts"
        )
    
    
        suspend fun readOneContactFull(id: Long) =
            requestAdapter.readProjection<ContactFull, ContactFullDto>(
                id = id,
                uri = "/contactFulls",
                projectionName = "full"
            )
    
        suspend fun update(obj: Contact) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: Contact) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "/contacts")
    
    
        suspend fun readAddress(obj: Contact) =
            requestAdapter.readAssociationProjection<Contact, Address, AddressDto>(
                obj = obj,
                linkName = "address"
            )
        
        
        
        suspend fun setAddress(obj: Contact, child: Address) =
            requestAdapter.updateAssociation(
                objToBeSetted = child,
                objWithAssociation = obj,
                property = "address"
            )
        
        suspend fun deleteFromAddress(obj: Contact, childToDelete: Address) =
            requestAdapter.request.delete(
                url = "/contacts/${obj.id}/address/${childToDelete.id}"
            )
        
        
        suspend fun readOwner(obj: Contact) =
            requestAdapter.readAssociationProjection<Contact, User, UserDto>(
                obj = obj,
                linkName = "owner"
            )
        
        
        
        suspend fun setOwner(obj: Contact, child: User) =
            requestAdapter.updateAssociation(
                objToBeSetted = child,
                objWithAssociation = obj,
                property = "owner"
            )
        
        suspend fun deleteFromOwner(obj: Contact, childToDelete: User) =
            requestAdapter.request.delete(
                url = "/contacts/${obj.id}/owner/${childToDelete.id}"
            )
    
        suspend fun searchContactsByRegex(nameRegex: String): List<Contact> {
        
            val url = "/search/contactsByRegex".appendParams(
                "nameRegex" to nameRegex
            )
        
            return requestAdapter.doListRequest<Contact, ContactDto>(
                url = url,
                embeddedPropName = "contacts",
                type = object : TypeReference<ApiHateoasList<ContactDto, Contact>>() {}
            )
        }
        
        suspend fun searchContactsByRegexContactFull(nameRegex: String): List<ContactFull> {
        
            val url = "/search/contactsByRegex".appendParams(
                "nameRegex" to nameRegex
            )
        
            return requestAdapter.doListRequest<ContactFull, ContactFullDto>(
                url = url,
                embeddedPropName = "contacts",
                projectionName = "full",
                type = object : TypeReference<ApiHateoasList<ContactFullDto, ContactFull>>() {}
            )
        }
        
        
        
        suspend fun searchFindByNameContaining(name: String, page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<Contact> {
        
            val url = "/contacts/search/findByNameContaining".appendParams(
                "name" to name
            )
        
            return requestAdapter.doPageRequest<Contact, ContactDto>(
                url = url,
                embeddedPropName = "contacts",
                
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<ContactDto, Contact>>() {}
            )
        }
        
        suspend fun searchFindByNameContainingContactFull(name: String, page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<ContactFull> {
        
            val url = "/contacts/search/findByNameContaining".appendParams(
                "name" to name
            )
        
            return requestAdapter.doPageRequest<ContactFull, ContactFullDto>(
                url = url,
                embeddedPropName = "contacts",
                projectionName = "full",
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<ContactFullDto, ContactFull>>() {}
            )
        }
        
        
        
        suspend fun searchFindByNames(firstName: String, lastName: String): Contact? {
        
            val url = "/contacts/search/findByNames".appendParams(
                "firstName" to firstName,
                    "lastName" to lastName
            )
        
            return requestAdapter.doSingleRequest<Contact, ContactDto>(
                url = url
            )
        }
        
        suspend fun searchFindByNamesContactFull(firstName: String, lastName: String): ContactFull? {
        
            val url = "/contacts/search/findByNames".appendParams(
                "firstName" to firstName,
                    "lastName" to lastName
            )
        
            return requestAdapter.doSingleRequest<ContactFull, ContactFullDto>(
                url = url,
                projectionName = "full"
            )
        }
    
        suspend fun customPostCreateOrUpdate(userName: String, firstName: String, lastName: String, number: String, street: String, zip: String, city: String, country: String): Contact {
        
            val url = "/api/custom/contacts/createOrUpdate".appendParams("userName" to userName,
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "number" to number,
                    "street" to street,
                    "zip" to zip,
                    "city" to city,
                    "country" to country)
        
            return requestAdapter.doSingleRequest<Contact, ContactDto>(
                url = url,
                method = "POST",
                ignoreBasePath = true
            )
        }
        
        suspend fun customPostCreateOrUpdateContactFull(userName: String, firstName: String, lastName: String, number: String, street: String, zip: String, city: String, country: String): ContactFull? {
        
            val url = "/api/custom/contacts/createOrUpdate".appendParams("userName" to userName,
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "number" to number,
                    "street" to street,
                    "zip" to zip,
                    "city" to city,
                    "country" to country)
        
            return requestAdapter.doSingleRequest<ContactFull, ContactFullDto>(
                url = url,
                method = "POST",
                projectionName = "full",
                ignoreBasePath = true
            )
        }
    
    }
    
    open class PrimitiveTestEntityClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: PrimitiveTestEntityBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "/primitiveTestEntities"
        )
    
        @Deprecated(message = "from now on an empty constructor is available in base types (as well as a builder)")
        fun build() = PrimitiveTestEntityBase(
                id = -1L,
                booleanTrue = false,
                date2000_6_12 = LocalDate.parse("1970-01-01"),
                dateTime2000_1_1_12_30 = LocalDateTime.parse("1970-01-01T00:00:00"),
                int32 = 0,
                intMinusBillion = 0,
                long64 = 0L,
                optionalIntBillion = 0,
                optionalIntNull = 0,
                stringText = "",
                _links = null
            )
    
        suspend fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<PrimitiveTestEntity, PrimitiveTestEntityDto>(
                projectionName = null,
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<PrimitiveTestEntityDto, PrimitiveTestEntity>>() {}
            )
    
        
    
        private suspend inline fun <reified T: ApiObj<U>, reified U: ApiDto<T>> readProjections(
                projectionName: String?, page: Int?, size: Int?, sort: String?,
                type: TypeReference<ApiHateoasPage<U, T>>
        ) =
            requestAdapter.doPageRequest<T, U>(
                url = "/primitiveTestEntities",
                embeddedPropName = "primitiveTestEntities",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<PrimitiveTestEntity, PrimitiveTestEntityDto>(
            id = id,
            uri = "/primitiveTestEntities"
        )
    
    
        
    
        suspend fun update(obj: PrimitiveTestEntity) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: PrimitiveTestEntity) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "/primitiveTestEntities")
    
    
        
    
        
    
        suspend fun customPostMixedCreateByInt32(int32: Int, body: PrimitiveTestEntityBase, long64: Long): PrimitiveTestEntity {
        
            val url = "/api/custom/primitiveTestEntities/mixedCreate/$int32".appendParams("long64" to long64)
        
            return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto, PrimitiveTestEntityBase>(
                url = url,
                method = "POST",
                body = body,
                ignoreBasePath = true
            )
        }
        
        
        
        
        
        suspend fun customPostNoBodyCreateByInt32(int32: Int, long64: Long): PrimitiveTestEntity {
        
            val url = "/api/custom/primitiveTestEntities/noBodyCreate/$int32".appendParams("long64" to long64)
        
            return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
                url = url,
                method = "POST",
                ignoreBasePath = true
            )
        }
        
        
        
        
        
        suspend fun customPostNoPathVariableCreate(body: PrimitiveTestEntityBase, long64: Long): PrimitiveTestEntity {
        
            val url = "/api/custom/primitiveTestEntities/noPathVariableCreate".appendParams("long64" to long64)
        
            return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto, PrimitiveTestEntityBase>(
                url = url,
                method = "POST",
                body = body,
                ignoreBasePath = true
            )
        }
        
        
        
        
        
        suspend fun customPostNoRequestParamCreateByInt32(int32: Int, body: PrimitiveTestEntityBase): PrimitiveTestEntity {
        
            val url = "/api/custom/primitiveTestEntities/noRequestParamCreate/$int32".appendParams()
        
            return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto, PrimitiveTestEntityBase>(
                url = url,
                method = "POST",
                body = body,
                ignoreBasePath = true
            )
        }
        
        
        
        
        
        suspend fun customPostPathVariableCreateByInt32ByLong64CustomByIntMinusBillionByStringTextByBooleanTrueByDateCustom(int32: Int, long64Custom: Long, intMinusBillion: Int, stringText: String, booleanTrue: Boolean, dateCustom: LocalDate): PrimitiveTestEntity {
        
            val url = "/api/custom/primitiveTestEntities/pathVariableCreate/$int32/$long64Custom/$intMinusBillion/$stringText/$booleanTrue/$dateCustom".appendParams()
        
            return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
                url = url,
                method = "POST",
                ignoreBasePath = true
            )
        }
        
        
        
        
        
        suspend fun customPostRequestParamCreate(int32: Int, long64Custom: Long, intMinusBillion: Int, stringText: String, booleanTrue: Boolean, dateCustom: LocalDate, optionalIntNull: Int?, optionalIntBillion: Int?, dateTime2000_1_1_12_30: LocalDateTime?): PrimitiveTestEntity {
        
            val url = "/api/custom/primitiveTestEntities/requestParamCreate".appendParams("int32" to int32,
                    "long64Custom" to long64Custom,
                    "optionalIntNull" to optionalIntNull,
                    "optionalIntBillion" to optionalIntBillion,
                    "intMinusBillion" to intMinusBillion,
                    "stringText" to stringText,
                    "booleanTrue" to booleanTrue,
                    "dateCustom" to dateCustom,
                    "dateTime2000_1_1_12_30" to dateTime2000_1_1_12_30)
        
            return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
                url = url,
                method = "POST",
                ignoreBasePath = true
            )
        }
        
        
        
        
        
        suspend fun customPostRequestBodyCreate(body: PrimitiveTestEntityBase): PrimitiveTestEntity {
        
            val url = "/api/custom/primitiveTestEntities/requestBodyCreate".appendParams()
        
            return requestAdapter.doSingleRequest<PrimitiveTestEntity, PrimitiveTestEntityDto, PrimitiveTestEntityBase>(
                url = url,
                method = "POST",
                body = body,
                ignoreBasePath = true
            )
        }
        
        
        
        
        
        suspend fun customGetReturnList(): List<PrimitiveTestEntity> {
        
            val url = "/api/custom/primitiveTestEntities/returnList".appendParams()
        
            return requestAdapter.doListRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
                url = url,
                method = "GET",
                embeddedPropName = "primitiveTestEntities",
                ignoreBasePath = true,
                type = object : TypeReference<ApiHateoasList<PrimitiveTestEntityDto, PrimitiveTestEntity>>() {}
            )
        }
        
        
        
        
        
        suspend fun customGetReturnPaged(page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<PrimitiveTestEntity> {
        
            val url = "/api/custom/primitiveTestEntities/returnPaged".appendParams()
        
            return requestAdapter.doPageRequest<PrimitiveTestEntity, PrimitiveTestEntityDto>(
                url = url,
                method = "GET",
                embeddedPropName = "primitiveTestEntities",
                
                page = page,
                size = size,
                sort = sort,
                ignoreBasePath = true,
                type = object : TypeReference<ApiHateoasPage<PrimitiveTestEntityDto, PrimitiveTestEntity>>() {}
            )
        }
        
        
        
        
        
        suspend fun customGetReturnVoid(): Unit {
        
            val url = "/api/custom/primitiveTestEntities/returnVoid".appendParams()
        
            return requestAdapter.doVoidRequest(
                url = url,
                method = "GET",
                ignoreBasePath = true
            )
        }
        
        
    
    }
    
    open class RelTestEntityClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: RelTestEntityBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "/relTestEntities"
        )
    
        @Deprecated(message = "from now on an empty constructor is available in base types (as well as a builder)")
        fun build() = RelTestEntityBase(
                id = -1L,
                _links = null
            )
    
        suspend fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<RelTestEntity, RelTestEntityDto>(
                projectionName = null,
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<RelTestEntityDto, RelTestEntity>>() {}
            )
    
        suspend fun readAllFullRelTestEntity(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<FullRelTestEntity, FullRelTestEntityDto>(
                projectionName = "full",
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<FullRelTestEntityDto, FullRelTestEntity>>() {}
            )
    
        private suspend inline fun <reified T: ApiObj<U>, reified U: ApiDto<T>> readProjections(
                projectionName: String?, page: Int?, size: Int?, sort: String?,
                type: TypeReference<ApiHateoasPage<U, T>>
        ) =
            requestAdapter.doPageRequest<T, U>(
                url = "/relTestEntities",
                embeddedPropName = "relTestEntities",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<RelTestEntity, RelTestEntityDto>(
            id = id,
            uri = "/relTestEntities"
        )
    
    
        suspend fun readOneFullRelTestEntity(id: Long) =
            requestAdapter.readProjection<FullRelTestEntity, FullRelTestEntityDto>(
                id = id,
                uri = "/fullRelTestEntities",
                projectionName = "full"
            )
    
        suspend fun update(obj: RelTestEntity) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: RelTestEntity) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "/relTestEntities")
    
    
        suspend fun readManyToMany(obj: RelTestEntity) =
            requestAdapter.readListAssociationProjection<RelTestEntity, User, UserDto>(
                obj = obj,
                linkName = "manyToMany",
                property = "users",
                type = object: TypeReference<ApiHateoasList<UserDto, User>>() {}
            )
        
        
        
        suspend fun setManyToMany(obj: RelTestEntity, children: List<User>) =
            requestAdapter.updateObjectCollection(
                nextCollection = children,
                objectWithCollection = obj,
                property = "manyToMany"
            )
        
        suspend fun addToManyToMany(obj: RelTestEntity, childToAdd: User) =
            requestAdapter.addObjectToCollection(
                objToBeAdd = childToAdd,
                objectWithCollection = obj,
                property = "manyToMany"
            )
        
        suspend fun deleteFromManyToMany(obj: RelTestEntity, childToDelete: User) =
            requestAdapter.request.delete(
                url = "/relTestEntities/${obj.id}/manyToMany/${childToDelete.id}"
            )
        
        
        suspend fun readManyToOneOptional(obj: RelTestEntity) =
            requestAdapter.readAssociationProjection<RelTestEntity, User, UserDto>(
                obj = obj,
                linkName = "manyToOneOptional"
            )
        
        
        
        suspend fun setManyToOneOptional(obj: RelTestEntity, child: User) =
            requestAdapter.updateAssociation(
                objToBeSetted = child,
                objWithAssociation = obj,
                property = "manyToOneOptional"
            )
        
        suspend fun deleteFromManyToOneOptional(obj: RelTestEntity, childToDelete: User) =
            requestAdapter.request.delete(
                url = "/relTestEntities/${obj.id}/manyToOneOptional/${childToDelete.id}"
            )
        
        
        suspend fun readManyToOneRequired(obj: RelTestEntity) =
            requestAdapter.readAssociationProjection<RelTestEntity, User, UserDto>(
                obj = obj,
                linkName = "manyToOneRequired"
            )
        
        
        
        suspend fun setManyToOneRequired(obj: RelTestEntity, child: User) =
            requestAdapter.updateAssociation(
                objToBeSetted = child,
                objWithAssociation = obj,
                property = "manyToOneRequired"
            )
        
        
        
        
        suspend fun readOneToMany(obj: RelTestEntity) =
            requestAdapter.readListAssociationProjection<RelTestEntity, User, UserDto>(
                obj = obj,
                linkName = "oneToMany",
                property = "users",
                type = object: TypeReference<ApiHateoasList<UserDto, User>>() {}
            )
        
        
        
        suspend fun setOneToMany(obj: RelTestEntity, children: List<User>) =
            requestAdapter.updateObjectCollection(
                nextCollection = children,
                objectWithCollection = obj,
                property = "oneToMany"
            )
        
        suspend fun addToOneToMany(obj: RelTestEntity, childToAdd: User) =
            requestAdapter.addObjectToCollection(
                objToBeAdd = childToAdd,
                objectWithCollection = obj,
                property = "oneToMany"
            )
        
        suspend fun deleteFromOneToMany(obj: RelTestEntity, childToDelete: User) =
            requestAdapter.request.delete(
                url = "/relTestEntities/${obj.id}/oneToMany/${childToDelete.id}"
            )
        
        
        suspend fun readOneToOneOptional(obj: RelTestEntity) =
            requestAdapter.readAssociationProjection<RelTestEntity, User, UserDto>(
                obj = obj,
                linkName = "oneToOneOptional"
            )
        
        
        
        suspend fun setOneToOneOptional(obj: RelTestEntity, child: User) =
            requestAdapter.updateAssociation(
                objToBeSetted = child,
                objWithAssociation = obj,
                property = "oneToOneOptional"
            )
        
        suspend fun deleteFromOneToOneOptional(obj: RelTestEntity, childToDelete: User) =
            requestAdapter.request.delete(
                url = "/relTestEntities/${obj.id}/oneToOneOptional/${childToDelete.id}"
            )
        
        
        suspend fun readOneToOneRequired(obj: RelTestEntity) =
            requestAdapter.readAssociationProjection<RelTestEntity, User, UserDto>(
                obj = obj,
                linkName = "oneToOneRequired"
            )
        
        
        
        suspend fun setOneToOneRequired(obj: RelTestEntity, child: User) =
            requestAdapter.updateAssociation(
                objToBeSetted = child,
                objWithAssociation = obj,
                property = "oneToOneRequired"
            )
        
        
    
        
    
        
    
    }
    
    open class UserClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: UserBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "/users"
        )
    
        @Deprecated(message = "from now on an empty constructor is available in base types (as well as a builder)")
        fun build() = UserBase(
                id = -1L,
                name = "",
                _links = null
            )
    
        suspend fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<User, UserDto>(
                projectionName = null,
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<UserDto, User>>() {}
            )
    
        
    
        private suspend inline fun <reified T: ApiObj<U>, reified U: ApiDto<T>> readProjections(
                projectionName: String?, page: Int?, size: Int?, sort: String?,
                type: TypeReference<ApiHateoasPage<U, T>>
        ) =
            requestAdapter.doPageRequest<T, U>(
                url = "/users",
                embeddedPropName = "users",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<User, UserDto>(
            id = id,
            uri = "/users"
        )
    
    
        
    
        suspend fun update(obj: User) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: User) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "/users")
    
    
        suspend fun readContacts(obj: User) =
            requestAdapter.readListAssociationProjection<User, Contact, ContactDto>(
                obj = obj,
                linkName = "contacts",
                property = "contacts",
                type = object: TypeReference<ApiHateoasList<ContactDto, Contact>>() {}
            )
        
        suspend fun readContactsContactFull(obj: User) =
            requestAdapter.readListAssociationProjection<User, ContactFull, ContactFullDto>(
                obj = obj,
                linkName = "contacts",
                property = "contacts",
                projectionName = "full",
                type = object: TypeReference<ApiHateoasList<ContactFullDto, ContactFull>>() {}
            )
        
        suspend fun setContacts(obj: User, children: List<Contact>) =
            requestAdapter.updateObjectCollection(
                nextCollection = children,
                objectWithCollection = obj,
                property = "contacts"
            )
        
        suspend fun addToContacts(obj: User, childToAdd: Contact) =
            requestAdapter.addObjectToCollection(
                objToBeAdd = childToAdd,
                objectWithCollection = obj,
                property = "contacts"
            )
        
        suspend fun deleteFromContacts(obj: User, childToDelete: Contact) =
            requestAdapter.request.delete(
                url = "/users/${obj.id}/contacts/${childToDelete.id}"
            )
    
        suspend fun searchFindUserByName(name: String): User? {
        
            val url = "/users/search/findUserByName".appendParams(
                "name" to name
            )
        
            return requestAdapter.doSingleRequest<User, UserDto>(
                url = url
            )
        }
        
        
    
        
    
    }

    open class AddressRepository( val client: AddressClient ) {
    
        fun create(obj: AddressBase) =
            runBlocking { client.create(obj) }
    
        fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAll(page, size, sort) }
    
        
    
        fun readOne(id: Long) =
            runBlocking { client.readOne(id) }
    
        
    
        fun update(obj: Address) =
            runBlocking { client.update(obj) }
    
        fun delete(obj: Address) =
            runBlocking { client.delete(obj) }
    
        fun delete(id: Long) =
            runBlocking { client.delete(id) }
    
    
        
    
        
    
        
    
    }
    
    open class ContactRepository( val client: ContactClient ) {
    
        fun create(obj: ContactBase) =
            runBlocking { client.create(obj) }
    
        fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAll(page, size, sort) }
    
        fun readAllContactFull(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAllContactFull(page, size, sort) }
    
        fun readOne(id: Long) =
            runBlocking { client.readOne(id) }
    
        fun readOneContactFull(id: Long) =
            runBlocking { client.readOneContactFull(id) }
    
        fun update(obj: Contact) =
            runBlocking { client.update(obj) }
    
        fun delete(obj: Contact) =
            runBlocking { client.delete(obj) }
    
        fun delete(id: Long) =
            runBlocking { client.delete(id) }
    
    
        fun readAddress(obj: Contact) =
            runBlocking { client.readAddress(obj) }
        
        
        
        fun setAddress(obj: Contact, child: Address) =
            runBlocking { client.setAddress(obj, child) }
        
        fun deleteFromAddress(obj: Contact, childToDelete: Address) =
            runBlocking { client.deleteFromAddress(obj, childToDelete) }
        
        
        fun readOwner(obj: Contact) =
            runBlocking { client.readOwner(obj) }
        
        
        
        fun setOwner(obj: Contact, child: User) =
            runBlocking { client.setOwner(obj, child) }
        
        fun deleteFromOwner(obj: Contact, childToDelete: User) =
            runBlocking { client.deleteFromOwner(obj, childToDelete) }
    
        fun searchContactsByRegex(nameRegex: String): List<Contact> =
            runBlocking { client.searchContactsByRegex(nameRegex) }
        
        fun searchContactsByRegexContactFull(nameRegex: String): List<ContactFull> =
            runBlocking { client.searchContactsByRegexContactFull(nameRegex) }
        
        
        
        fun searchFindByNameContaining(name: String, page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<Contact> =
            runBlocking { client.searchFindByNameContaining(name, page, size, sort) }
        
        fun searchFindByNameContainingContactFull(name: String, page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<ContactFull> =
            runBlocking { client.searchFindByNameContainingContactFull(name, page, size, sort) }
        
        
        
        fun searchFindByNames(firstName: String, lastName: String): Contact? =
            runBlocking { client.searchFindByNames(firstName, lastName) }
        
        fun searchFindByNamesContactFull(firstName: String, lastName: String): ContactFull? =
            runBlocking { client.searchFindByNamesContactFull(firstName, lastName) }
    
        fun customPostCreateOrUpdate(userName: String, firstName: String, lastName: String, number: String, street: String, zip: String, city: String, country: String): Contact =
                runBlocking { client.customPostCreateOrUpdate(userName, firstName, lastName, number, street, zip, city, country) }
        
        fun customPostCreateOrUpdateContactFull(userName: String, firstName: String, lastName: String, number: String, street: String, zip: String, city: String, country: String): ContactFull? =
                runBlocking { client.customPostCreateOrUpdateContactFull(userName, firstName, lastName, number, street, zip, city, country) }
    
    }
    
    open class PrimitiveTestEntityRepository( val client: PrimitiveTestEntityClient ) {
    
        fun create(obj: PrimitiveTestEntityBase) =
            runBlocking { client.create(obj) }
    
        fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAll(page, size, sort) }
    
        
    
        fun readOne(id: Long) =
            runBlocking { client.readOne(id) }
    
        
    
        fun update(obj: PrimitiveTestEntity) =
            runBlocking { client.update(obj) }
    
        fun delete(obj: PrimitiveTestEntity) =
            runBlocking { client.delete(obj) }
    
        fun delete(id: Long) =
            runBlocking { client.delete(id) }
    
    
        
    
        
    
        fun customPostMixedCreateByInt32(int32: Int, body: PrimitiveTestEntityBase, long64: Long): PrimitiveTestEntity =
                runBlocking { client.customPostMixedCreateByInt32(int32, body, long64) }
        
        
        
        
        
        fun customPostNoBodyCreateByInt32(int32: Int, long64: Long): PrimitiveTestEntity =
                runBlocking { client.customPostNoBodyCreateByInt32(int32, long64) }
        
        
        
        
        
        fun customPostNoPathVariableCreate(body: PrimitiveTestEntityBase, long64: Long): PrimitiveTestEntity =
                runBlocking { client.customPostNoPathVariableCreate(body, long64) }
        
        
        
        
        
        fun customPostNoRequestParamCreateByInt32(int32: Int, body: PrimitiveTestEntityBase): PrimitiveTestEntity =
                runBlocking { client.customPostNoRequestParamCreateByInt32(int32, body) }
        
        
        
        
        
        fun customPostPathVariableCreateByInt32ByLong64CustomByIntMinusBillionByStringTextByBooleanTrueByDateCustom(int32: Int, long64Custom: Long, intMinusBillion: Int, stringText: String, booleanTrue: Boolean, dateCustom: LocalDate): PrimitiveTestEntity =
                runBlocking { client.customPostPathVariableCreateByInt32ByLong64CustomByIntMinusBillionByStringTextByBooleanTrueByDateCustom(int32, long64Custom, intMinusBillion, stringText, booleanTrue, dateCustom) }
        
        
        
        
        
        fun customPostRequestParamCreate(int32: Int, long64Custom: Long, intMinusBillion: Int, stringText: String, booleanTrue: Boolean, dateCustom: LocalDate, optionalIntNull: Int?, optionalIntBillion: Int?, dateTime2000_1_1_12_30: LocalDateTime?): PrimitiveTestEntity =
                runBlocking { client.customPostRequestParamCreate(int32, long64Custom, intMinusBillion, stringText, booleanTrue, dateCustom, optionalIntNull, optionalIntBillion, dateTime2000_1_1_12_30) }
        
        
        
        
        
        fun customPostRequestBodyCreate(body: PrimitiveTestEntityBase): PrimitiveTestEntity =
                runBlocking { client.customPostRequestBodyCreate(body) }
        
        
        
        
        
        fun customGetReturnList(): List<PrimitiveTestEntity> =
                runBlocking { client.customGetReturnList() }
        
        
        
        
        
        fun customGetReturnPaged(page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<PrimitiveTestEntity> =
                runBlocking { client.customGetReturnPaged(page, size, sort) }
        
        
        
        
        
        fun customGetReturnVoid(): Unit =
                runBlocking { client.customGetReturnVoid() }
        
        
    
    }
    
    open class RelTestEntityRepository( val client: RelTestEntityClient ) {
    
        fun create(obj: RelTestEntityBase) =
            runBlocking { client.create(obj) }
    
        fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAll(page, size, sort) }
    
        fun readAllFullRelTestEntity(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAllFullRelTestEntity(page, size, sort) }
    
        fun readOne(id: Long) =
            runBlocking { client.readOne(id) }
    
        fun readOneFullRelTestEntity(id: Long) =
            runBlocking { client.readOneFullRelTestEntity(id) }
    
        fun update(obj: RelTestEntity) =
            runBlocking { client.update(obj) }
    
        fun delete(obj: RelTestEntity) =
            runBlocking { client.delete(obj) }
    
        fun delete(id: Long) =
            runBlocking { client.delete(id) }
    
    
        fun readManyToMany(obj: RelTestEntity) =
            runBlocking { client.readManyToMany(obj) }
        
        
        
        fun setManyToMany(obj: RelTestEntity, children: List<User>) =
            runBlocking { client.setManyToMany(obj, children) }
        
        fun addToManyToMany(obj: RelTestEntity, childToAdd: User) =
            runBlocking { client.addToManyToMany(obj, childToAdd) }
        
        fun deleteFromManyToMany(obj: RelTestEntity, childToDelete: User) =
            runBlocking { client.deleteFromManyToMany(obj, childToDelete) }
        
        
        fun readManyToOneOptional(obj: RelTestEntity) =
            runBlocking { client.readManyToOneOptional(obj) }
        
        
        
        fun setManyToOneOptional(obj: RelTestEntity, child: User) =
            runBlocking { client.setManyToOneOptional(obj, child) }
        
        fun deleteFromManyToOneOptional(obj: RelTestEntity, childToDelete: User) =
            runBlocking { client.deleteFromManyToOneOptional(obj, childToDelete) }
        
        
        fun readManyToOneRequired(obj: RelTestEntity) =
            runBlocking { client.readManyToOneRequired(obj) }
        
        
        
        fun setManyToOneRequired(obj: RelTestEntity, child: User) =
            runBlocking { client.setManyToOneRequired(obj, child) }
        
        
        
        
        fun readOneToMany(obj: RelTestEntity) =
            runBlocking { client.readOneToMany(obj) }
        
        
        
        fun setOneToMany(obj: RelTestEntity, children: List<User>) =
            runBlocking { client.setOneToMany(obj, children) }
        
        fun addToOneToMany(obj: RelTestEntity, childToAdd: User) =
            runBlocking { client.addToOneToMany(obj, childToAdd) }
        
        fun deleteFromOneToMany(obj: RelTestEntity, childToDelete: User) =
            runBlocking { client.deleteFromOneToMany(obj, childToDelete) }
        
        
        fun readOneToOneOptional(obj: RelTestEntity) =
            runBlocking { client.readOneToOneOptional(obj) }
        
        
        
        fun setOneToOneOptional(obj: RelTestEntity, child: User) =
            runBlocking { client.setOneToOneOptional(obj, child) }
        
        fun deleteFromOneToOneOptional(obj: RelTestEntity, childToDelete: User) =
            runBlocking { client.deleteFromOneToOneOptional(obj, childToDelete) }
        
        
        fun readOneToOneRequired(obj: RelTestEntity) =
            runBlocking { client.readOneToOneRequired(obj) }
        
        
        
        fun setOneToOneRequired(obj: RelTestEntity, child: User) =
            runBlocking { client.setOneToOneRequired(obj, child) }
        
        
    
        
    
        
    
    }
    
    open class UserRepository( val client: UserClient ) {
    
        fun create(obj: UserBase) =
            runBlocking { client.create(obj) }
    
        fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAll(page, size, sort) }
    
        
    
        fun readOne(id: Long) =
            runBlocking { client.readOne(id) }
    
        
    
        fun update(obj: User) =
            runBlocking { client.update(obj) }
    
        fun delete(obj: User) =
            runBlocking { client.delete(obj) }
    
        fun delete(id: Long) =
            runBlocking { client.delete(id) }
    
    
        fun readContacts(obj: User) =
            runBlocking { client.readContacts(obj) }
        
        fun readContactsContactFull(obj: User) =
            runBlocking { client.readContactsContactFull(obj) }
        
        fun setContacts(obj: User, children: List<Contact>) =
            runBlocking { client.setContacts(obj, children) }
        
        fun addToContacts(obj: User, childToAdd: Contact) =
            runBlocking { client.addToContacts(obj, childToAdd) }
        
        fun deleteFromContacts(obj: User, childToDelete: Contact) =
            runBlocking { client.deleteFromContacts(obj, childToDelete) }
    
        fun searchFindUserByName(name: String): User? =
            runBlocking { client.searchFindUserByName(name) }
        
        
    
        
    
    }