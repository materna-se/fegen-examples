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
    import de.materna.fegen.example.gradle.kotlin.api.controller.CustomEndpointControllerClient
        import de.materna.fegen.example.gradle.kotlin.api.controller.TestRestControllerClient

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
        open val ignoredSearchEntityClient by lazy { IgnoredSearchEntityClient(apiClient = this, requestAdapter = adapter) }
        open val ignoredSearchEntityRepository by lazy { IgnoredSearchEntityRepository(client = ignoredSearchEntityClient) }
        open val primitiveTestEntityClient by lazy { PrimitiveTestEntityClient(apiClient = this, requestAdapter = adapter) }
        open val primitiveTestEntityRepository by lazy { PrimitiveTestEntityRepository(client = primitiveTestEntityClient) }
        open val relTestEntityClient by lazy { RelTestEntityClient(apiClient = this, requestAdapter = adapter) }
        open val relTestEntityRepository by lazy { RelTestEntityRepository(client = relTestEntityClient) }
        open val securedEntityClient by lazy { SecuredEntityClient(apiClient = this, requestAdapter = adapter) }
        open val securedEntityRepository by lazy { SecuredEntityRepository(client = securedEntityClient) }
        open val userClient by lazy { UserClient(apiClient = this, requestAdapter = adapter) }
        open val userRepository by lazy { UserRepository(client = userClient) }
        open val customEndpointControllerClient by lazy { CustomEndpointControllerClient(adapter) }
        open val testRestControllerClient by lazy { TestRestControllerClient(adapter) }
    }

    open class AddressClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: AddressBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "api/contactAddresses"
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
                url = "api/contactAddresses",
                embeddedPropName = "addresses",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<Address, AddressDto>(
            id = id,
            uri = "api/contactAddresses"
        )
    
    
        
    
        suspend fun update(obj: Address) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: Address) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "api/contactAddresses")
        
        suspend fun allowedMethods(): EntitySecurity = EntitySecurity.fetch(requestAdapter.request, "/api/contactAddresses")
    
    
        
    
        
    }
    
    open class ContactClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: ContactBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "api/contacts"
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
                url = "api/contacts",
                embeddedPropName = "contacts",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<Contact, ContactDto>(
            id = id,
            uri = "api/contacts"
        )
    
    
        suspend fun readOneContactFull(id: Long) =
            requestAdapter.readProjection<ContactFull, ContactFullDto>(
                id = id,
                uri = "api/contacts",
                projectionName = "full"
            )
    
        suspend fun update(obj: Contact) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: Contact) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "api/contacts")
        
        suspend fun allowedMethods(): EntitySecurity = EntitySecurity.fetch(requestAdapter.request, "/api/contacts")
    
    
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
                url = "api/contacts/${obj.id}/address/${childToDelete.id}"
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
                url = "api/contacts/${obj.id}/owner/${childToDelete.id}"
            )
    
        suspend fun searchFindByNameContaining(name: String, page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<Contact> {
        
            val url = "api/contacts/search/findByNameContaining".appendParams(
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
        
            val url = "api/contacts/search/findByNameContaining".appendParams(
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
        
            val url = "api/contacts/search/findByNames".appendParams(
                "firstName" to firstName,
                    "lastName" to lastName
            )
        
            return requestAdapter.doSingleRequest<Contact, ContactDto>(
                url = url
            )
        }
        
        suspend fun searchFindByNamesContactFull(firstName: String, lastName: String): ContactFull? {
        
            val url = "api/contacts/search/findByNames".appendParams(
                "firstName" to firstName,
                    "lastName" to lastName
            )
        
            return requestAdapter.doSingleRequest<ContactFull, ContactFullDto>(
                url = url,
                projectionName = "full"
            )
        }
        
        
        
        suspend fun searchContactsByRegex(nameRegex: String): List<Contact> {
        
            val url = "api/search/contactsByRegex".appendParams(
                "nameRegex" to nameRegex
            )
        
            return requestAdapter.doListRequest<Contact, ContactDto>(
                url = url,
                embeddedPropName = "contacts",
                type = object : TypeReference<ApiHateoasList<ContactDto, Contact>>() {}
            )
        }
        
        suspend fun searchContactsByRegexContactFull(nameRegex: String): List<ContactFull> {
        
            val url = "api/search/contactsByRegex".appendParams(
                "nameRegex" to nameRegex
            )
        
            return requestAdapter.doListRequest<ContactFull, ContactFullDto>(
                url = url,
                embeddedPropName = "contacts",
                projectionName = "full",
                type = object : TypeReference<ApiHateoasList<ContactFullDto, ContactFull>>() {}
            )
        }
    }
    
    open class IgnoredSearchEntityClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: IgnoredSearchEntityBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "api/ignoredSearchEntities"
        )
    
        suspend fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<IgnoredSearchEntity, IgnoredSearchEntityDto>(
                projectionName = null,
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<IgnoredSearchEntityDto, IgnoredSearchEntity>>() {}
            )
    
        
    
        private suspend inline fun <reified T: ApiObj<U>, reified U: ApiDto<T>> readProjections(
                projectionName: String?, page: Int?, size: Int?, sort: String?,
                type: TypeReference<ApiHateoasPage<U, T>>
        ) =
            requestAdapter.doPageRequest<T, U>(
                url = "api/ignoredSearchEntities",
                embeddedPropName = "ignoredSearchEntities",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<IgnoredSearchEntity, IgnoredSearchEntityDto>(
            id = id,
            uri = "api/ignoredSearchEntities"
        )
    
    
        
    
        suspend fun update(obj: IgnoredSearchEntity) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: IgnoredSearchEntity) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "api/ignoredSearchEntities")
        
        suspend fun allowedMethods(): EntitySecurity = EntitySecurity.fetch(requestAdapter.request, "/api/ignoredSearchEntities")
    
    
        
    
        
    }
    
    open class PrimitiveTestEntityClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: PrimitiveTestEntityBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "api/primitiveTestEntities"
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
                url = "api/primitiveTestEntities",
                embeddedPropName = "primitiveTestEntities",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<PrimitiveTestEntity, PrimitiveTestEntityDto>(
            id = id,
            uri = "api/primitiveTestEntities"
        )
    
    
        
    
        suspend fun update(obj: PrimitiveTestEntity) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: PrimitiveTestEntity) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "api/primitiveTestEntities")
        
        suspend fun allowedMethods(): EntitySecurity = EntitySecurity.fetch(requestAdapter.request, "/api/primitiveTestEntities")
    
    
        
    
        
    }
    
    open class RelTestEntityClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: RelTestEntityBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "api/relTestEntities"
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
                url = "api/relTestEntities",
                embeddedPropName = "relTestEntities",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<RelTestEntity, RelTestEntityDto>(
            id = id,
            uri = "api/relTestEntities"
        )
    
    
        suspend fun readOneFullRelTestEntity(id: Long) =
            requestAdapter.readProjection<FullRelTestEntity, FullRelTestEntityDto>(
                id = id,
                uri = "api/relTestEntities",
                projectionName = "full"
            )
    
        suspend fun update(obj: RelTestEntity) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: RelTestEntity) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "api/relTestEntities")
        
        suspend fun allowedMethods(): EntitySecurity = EntitySecurity.fetch(requestAdapter.request, "/api/relTestEntities")
    
    
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
                url = "api/relTestEntities/${obj.id}/manyToMany/${childToDelete.id}"
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
                url = "api/relTestEntities/${obj.id}/manyToOneOptional/${childToDelete.id}"
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
                url = "api/relTestEntities/${obj.id}/oneToMany/${childToDelete.id}"
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
                url = "api/relTestEntities/${obj.id}/oneToOneOptional/${childToDelete.id}"
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
    
    open class SecuredEntityClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: SecuredEntityBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "api/securedEntities"
        )
    
        suspend fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            readProjections<SecuredEntity, SecuredEntityDto>(
                projectionName = null,
                page = page,
                size = size,
                sort = sort,
                type = object : TypeReference<ApiHateoasPage<SecuredEntityDto, SecuredEntity>>() {}
            )
    
        
    
        private suspend inline fun <reified T: ApiObj<U>, reified U: ApiDto<T>> readProjections(
                projectionName: String?, page: Int?, size: Int?, sort: String?,
                type: TypeReference<ApiHateoasPage<U, T>>
        ) =
            requestAdapter.doPageRequest<T, U>(
                url = "api/securedEntities",
                embeddedPropName = "securedEntities",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<SecuredEntity, SecuredEntityDto>(
            id = id,
            uri = "api/securedEntities"
        )
    
    
        
    
        suspend fun update(obj: SecuredEntity) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: SecuredEntity) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "api/securedEntities")
        
        suspend fun allowedMethods(): EntitySecurity = EntitySecurity.fetch(requestAdapter.request, "/api/securedEntities")
    
    
        
    
        
    }
    
    open class UserClient(
            override val apiClient: ApiClient,
            override val requestAdapter: RequestAdapter
    ): BaseClient<ApiClient>(apiClient, requestAdapter) {
    
        suspend fun create(obj: UserBase) = requestAdapter.createObject(
            newObject = obj,
            createURI = "api/users"
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
                url = "api/users",
                embeddedPropName = "users",
                projectionName = projectionName,
                page = page,
                size = size,
                sort = sort,
                type = type
            )
    
        suspend fun readOne(id: Long) = requestAdapter.readProjection<User, UserDto>(
            id = id,
            uri = "api/users"
        )
    
    
        
    
        suspend fun update(obj: User) = requestAdapter.updateObject(obj)
    
        suspend fun delete(obj: User) = requestAdapter.deleteObject(obj)
    
        suspend fun delete(id: Long) = requestAdapter.deleteObject(id, "api/users")
        
        suspend fun allowedMethods(): EntitySecurity = EntitySecurity.fetch(requestAdapter.request, "/api/users")
    
    
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
                url = "api/users/${obj.id}/contacts/${childToDelete.id}"
            )
    
        suspend fun searchFindUserByName(name: String): User? {
        
            val url = "api/users/search/findUserByName".appendParams(
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
            
        fun allowedMethods() = runBlocking { client.allowedMethods() }
    
    
        
    
        
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
            
        fun allowedMethods() = runBlocking { client.allowedMethods() }
    
    
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
    
        fun searchFindByNameContaining(name: String, page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<Contact> =
            runBlocking { client.searchFindByNameContaining(name, page, size, sort) }
        
        fun searchFindByNameContainingContactFull(name: String, page: Int? = null, size: Int? = null, sort: String? = null): PagedItems<ContactFull> =
            runBlocking { client.searchFindByNameContainingContactFull(name, page, size, sort) }
        
        
        
        fun searchFindByNames(firstName: String, lastName: String): Contact? =
            runBlocking { client.searchFindByNames(firstName, lastName) }
        
        fun searchFindByNamesContactFull(firstName: String, lastName: String): ContactFull? =
            runBlocking { client.searchFindByNamesContactFull(firstName, lastName) }
        
        
        
        fun searchContactsByRegex(nameRegex: String): List<Contact> =
            runBlocking { client.searchContactsByRegex(nameRegex) }
        
        fun searchContactsByRegexContactFull(nameRegex: String): List<ContactFull> =
            runBlocking { client.searchContactsByRegexContactFull(nameRegex) }
    }
    
    open class IgnoredSearchEntityRepository( val client: IgnoredSearchEntityClient ) {
    
        fun create(obj: IgnoredSearchEntityBase) =
            runBlocking { client.create(obj) }
    
        fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAll(page, size, sort) }
    
        
    
        fun readOne(id: Long) =
            runBlocking { client.readOne(id) }
    
        
    
        fun update(obj: IgnoredSearchEntity) =
            runBlocking { client.update(obj) }
    
        fun delete(obj: IgnoredSearchEntity) =
            runBlocking { client.delete(obj) }
    
        fun delete(id: Long) =
            runBlocking { client.delete(id) }
            
        fun allowedMethods() = runBlocking { client.allowedMethods() }
    
    
        
    
        
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
            
        fun allowedMethods() = runBlocking { client.allowedMethods() }
    
    
        
    
        
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
            
        fun allowedMethods() = runBlocking { client.allowedMethods() }
    
    
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
    
    open class SecuredEntityRepository( val client: SecuredEntityClient ) {
    
        fun create(obj: SecuredEntityBase) =
            runBlocking { client.create(obj) }
    
        fun readAll(page: Int? = null, size: Int? = null, sort: String? = null) =
            runBlocking { client.readAll(page, size, sort) }
    
        
    
        fun readOne(id: Long) =
            runBlocking { client.readOne(id) }
    
        
    
        fun update(obj: SecuredEntity) =
            runBlocking { client.update(obj) }
    
        fun delete(obj: SecuredEntity) =
            runBlocking { client.delete(obj) }
    
        fun delete(id: Long) =
            runBlocking { client.delete(id) }
            
        fun allowedMethods() = runBlocking { client.allowedMethods() }
    
    
        
    
        
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
            
        fun allowedMethods() = runBlocking { client.allowedMethods() }
    
    
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