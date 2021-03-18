// This file has been generated by FeGen based on a spring application. Do not edit it.

import { ApiNavigationLink } from '@materna-se/fegen-runtime';

/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface AddressNew {
    city: string
    country: string | null
    street: string
    zip: string
    
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface AddressDto {
    city: string
    country: string | null
    street: string
    zip: string
    _links: {
        self: ApiNavigationLink
        
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `AddressDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface Address extends AddressDto {
    id: number
}


export interface AddressBaseProjection extends Address {
    
}

export interface ContactBaseProjection extends Contact {
    
}

export interface IgnoredSearchEntityBaseProjection extends IgnoredSearchEntity {
    
}

export interface NotExportedTestEntityBaseProjection extends NotExportedTestEntity {
    
}

export interface PrimitiveTestEntityBaseProjection extends PrimitiveTestEntity {
    
}

export interface RelTestEntityBaseProjection extends RelTestEntity {
    
}

export interface SecuredEntityBaseProjection extends SecuredEntity {
    
}

export interface UserBaseProjection extends User {
    
}

/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface ContactNew {
    firstName: string
    lastName: string
    number: string | null
    address: Address | null
    owner: User | null
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface ContactDto {
    firstName: string
    lastName: string
    number: string | null
    _links: {
        self: ApiNavigationLink
        address: ApiNavigationLink
        owner: ApiNavigationLink
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `ContactDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface Contact extends ContactDto {
    id: number
}


export interface ContactFull extends Contact {
    address: AddressBaseProjection | null
}

export interface EmbeddableTestEntity {
    embeddedLong: number
    embeddedNullableInt: number | null
    embeddedText: string
}

export interface FullRelTestEntity extends RelTestEntity {
    embedded: EmbeddableTestEntity | null
    embeddedNullable: OtherEmbeddableTestEntity | null
    manyToMany: UserBaseProjection[]
    manyToOneOptional: UserBaseProjection | null
    manyToOneRequired: UserBaseProjection
    oneToMany: UserBaseProjection[]
    oneToOneOptional: UserBaseProjection | null
    oneToOneRequired: UserBaseProjection
}

/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface IgnoredSearchEntityNew {
    text: string
    
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface IgnoredSearchEntityDto {
    text: string
    _links: {
        self: ApiNavigationLink
        
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `IgnoredSearchEntityDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface IgnoredSearchEntity extends IgnoredSearchEntityDto {
    id: number
}


/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface NotExportedTestEntityNew {
    text: string
    
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface NotExportedTestEntityDto {
    text: string
    _links: {
        self: ApiNavigationLink
        
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `NotExportedTestEntityDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface NotExportedTestEntity extends NotExportedTestEntityDto {
    id: number
}


export interface OtherEmbeddableTestEntity {
    embeddedNullableText: string | null
    otherEmbeddedNullableInt: number | null
}

/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface PrimitiveTestEntityNew {
    booleanTrue: boolean
    date2000_6_12: string
    dateTime2000_1_1_12_30: string | null
    int32: number
    intMinusBillion: number
    long64: number
    optionalIntBillion: number | null
    optionalIntNull: number | null
    stringText: string
    
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface PrimitiveTestEntityDto {
    booleanTrue: boolean
    date2000_6_12: string
    dateTime2000_1_1_12_30: string | null
    int32: number
    intMinusBillion: number
    long64: number
    optionalIntBillion: number | null
    optionalIntNull: number | null
    stringText: string
    _links: {
        self: ApiNavigationLink
        
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `PrimitiveTestEntityDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface PrimitiveTestEntity extends PrimitiveTestEntityDto {
    id: number
}


/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface RelTestEntityNew {
    testString: string
    embedded: EmbeddableTestEntity | null
    embeddedNullable: OtherEmbeddableTestEntity | null
    manyToMany: User[]
    manyToOneOptional: User | null
    manyToOneRequired: User
    oneToMany: User[]
    oneToOneOptional: User | null
    oneToOneRequired: User
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface RelTestEntityDto {
    testString: string
    embedded: EmbeddableTestEntity | null
    embeddedNullable: OtherEmbeddableTestEntity | null
    _links: {
        self: ApiNavigationLink
        manyToMany: ApiNavigationLink
        manyToOneOptional: ApiNavigationLink
        manyToOneRequired: ApiNavigationLink
        oneToMany: ApiNavigationLink
        oneToOneOptional: ApiNavigationLink
        oneToOneRequired: ApiNavigationLink
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `RelTestEntityDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface RelTestEntity extends RelTestEntityDto {
    id: number
}


/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface SecuredEntityNew {
    secretText: string
    
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface SecuredEntityDto {
    secretText: string
    _links: {
        self: ApiNavigationLink
        
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `SecuredEntityDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface SecuredEntity extends SecuredEntityDto {
    id: number
}


/**
 * This type is used for creaing this domain type. It can be created in the frontend
 * (in order to store it to the backend, for example) as it does neither have mandatory `_links` nor `id`.
 */
export interface UserNew {
    name: string
    contacts: Contact[]
}

/**
 * This type is used for data transfer. Each time we read an object of this domain type from a rest service,
 * this type will be returned.
 */
export interface UserDto {
    name: string
    _links: {
        self: ApiNavigationLink
        contacts: ApiNavigationLink
    }
}

/**
 * This type is the default type of choice in the frontend as it has an id (which can be added to the `UserDto`
 * via `apiHelper#getObjectId`). Consequently, this type is used for fields that reference this type.
 */
export interface User extends UserDto {
    id: number
}


export interface ComplexTestPojo {
    pojos: PrimitiveTestPojo[]
}

export interface CreateRequest {
    city: string
    country: string
    firstName: string
    lastName: string
    number: string
    street: string
    userName: string
    zip: string
}

export interface CyclicTestPojoA {
    b: CyclicTestPojoB | null
}

export interface CyclicTestPojoB {
    a: CyclicTestPojoA | null
}

export interface PrimitiveTestPojo {
    boolean: boolean | null
    number: number | null
    string: string | null
}

export interface RecursiveTestPojo {
    recursive: RecursiveTestPojo | null
}