// This file has been generated by FeGen based on a spring application. Do not edit it.

import {
    BaseClient, RequestAdapter,
    ApiHateoasObjectBase, ApiHateoasObjectReadMultiple, Items, PagedItems, ApiNavigationLinks, ApiBase,
    apiHelper, stringHelper
} from '@materna-se/fegen-runtime';
import { AddressBase, AddressDto, Address, ContactBase, ContactDto, Contact, TestEntityBase, TestEntityDto, TestEntity, UserBase, UserDto, User } from './Entities'
import {  } from './Entities'
import { AddressBaseProjection, ContactBaseProjection, TestEntityBaseProjection, UserBaseProjection, ContactFull } from './Entities'

export class ApiClient {
    public readonly addressClient: AddressClient;
    public readonly contactClient: ContactClient;
    public readonly testEntityClient: TestEntityClient;
    public readonly userClient: UserClient;

    private readonly baseUrl: string;

    constructor(requestAdapter?: RequestAdapter, baseUrl?: string) {
        this.baseUrl = baseUrl || "";
        const adapter = requestAdapter || new RequestAdapter(this.baseUrl);
        this.addressClient = new AddressClient(this, adapter);
        this.contactClient = new ContactClient(this, adapter);
        this.testEntityClient = new TestEntityClient(this, adapter);
        this.userClient = new UserClient(this, adapter);
    }
}

export class AddressClient extends BaseClient<ApiClient, AddressBase> {

    constructor(apiClient: ApiClient, requestAdapter?: RequestAdapter){
        super("/addresses", "addresses", apiClient, requestAdapter);
        this.readOne = this.readOne.bind(this);
        this.readProjection = this.readProjection.bind(this);
        
    }
  
    public static build(base: Partial<AddressBase> = {}): AddressBase {
        return {
            city: base.city !== undefined ? base.city : "",
            country: base.country !== undefined ? base.country : "",
            street: base.street !== undefined ? base.street : "",
            zip: base.zip !== undefined ? base.zip : ""
        }
    }
  
    public async readProjectionsAddressBaseProjection(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "city,ASC" | "city,DESC" | "street,ASC" | "street,DESC" | "zip,ASC" | "zip,DESC") : Promise<PagedItems<AddressBaseProjection>> {
        return this.readProjections<AddressBaseProjection>("baseProjection", page, size, sort);
    }
            
    public async readProjectionAddressBaseProjection(id: number): Promise<AddressBaseProjection| undefined> {
        return this.readProjection<AddressBaseProjection>(id, "baseProjection");
    }
    
    public async readAll(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "city,ASC" | "city,DESC" | "street,ASC" | "street,DESC" | "zip,ASC" | "zip,DESC") : Promise<PagedItems<Address>> {
        return await this.readProjections<Address>(undefined, page, size, sort);
    }
  
    public toDto(obj: Address): AddressDto {
        return obj;
    }

    public toBase<T extends AddressBase>(obj: T): AddressBase {
        return {
            city: obj.city,
            country: obj.country,
            street: obj.street,
            zip: obj.zip,
            
        };
    }
  
    
  
    
  
    
   
    
}

export class ContactClient extends BaseClient<ApiClient, ContactBase> {

    constructor(apiClient: ApiClient, requestAdapter?: RequestAdapter){
        super("/contacts", "contacts", apiClient, requestAdapter);
        this.readOne = this.readOne.bind(this);
        this.readProjection = this.readProjection.bind(this);
        this.readAddressProjection = this.readAddressProjection.bind(this);
            this.readOwnerProjection = this.readOwnerProjection.bind(this);
    }
  
    public static build(base: Partial<ContactBase> = {}): ContactBase {
        return {
            firstName: base.firstName !== undefined ? base.firstName : "",
            lastName: base.lastName !== undefined ? base.lastName : "",
            number: base.number !== undefined ? base.number : "",
            address: base.address !== undefined ? base.address : null,
            owner: base.owner !== undefined ? base.owner : null
        }
    }
  
    public async readProjectionsContactBaseProjection(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "firstName,ASC" | "firstName,DESC" | "lastName,ASC" | "lastName,DESC") : Promise<PagedItems<ContactBaseProjection>> {
        return this.readProjections<ContactBaseProjection>("baseProjection", page, size, sort);
    }
            
    public async readProjectionContactBaseProjection(id: number): Promise<ContactBaseProjection| undefined> {
        return this.readProjection<ContactBaseProjection>(id, "baseProjection");
    }
    

public async readProjectionsContactFull(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "firstName,ASC" | "firstName,DESC" | "lastName,ASC" | "lastName,DESC") : Promise<PagedItems<ContactFull>> {
        return this.readProjections<ContactFull>("full", page, size, sort);
    }
            
    public async readProjectionContactFull(id: number): Promise<ContactFull| undefined> {
        return this.readProjection<ContactFull>(id, "full");
    }
    
    public async readAll(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "firstName,ASC" | "firstName,DESC" | "lastName,ASC" | "lastName,DESC") : Promise<PagedItems<Contact>> {
        return await this.readProjections<Contact>(undefined, page, size, sort);
    }
  
    public toDto(obj: Contact): ContactDto {
        return obj;
    }

    public toBase<T extends ContactBase>(obj: T): ContactBase {
        return {
            firstName: obj.firstName,
            lastName: obj.lastName,
            number: obj.number,
            address: obj.address,
            owner: obj.owner,
        };
    }
  
    public async deleteFromAddress(returnType: Contact, childToDelete: Address) {
        await this._requestAdapter.getRequest().delete(`/contacts/${returnType.id}/address/${childToDelete.id}`);
    }
    
    public async deleteFromOwner(returnType: Contact, childToDelete: User) {
        await this._requestAdapter.getRequest().delete(`/contacts/${returnType.id}/owner/${childToDelete.id}`);
    }
  
    public async readAddress(obj: ContactDto): Promise<Address | undefined> {
        return this.readAddressProjection<Address>(obj);
    }
    
    public async readAddressProjectionAddressBaseProjection(obj: ContactDto): Promise<AddressBaseProjection | undefined> {
        return this.readAddressProjection<AddressBaseProjection>(obj, "baseProjection");
    }
    
    public async readAddressProjection<T extends ApiBase & { _links: ApiNavigationLinks }>(obj: ContactDto, projection?: string): Promise<T | undefined> {
        const hasProjection = (!!projection);
        let fullUrl = apiHelper.removeParamsFromNavigationHref(obj._links.address);
        fullUrl = hasProjection ? `${fullUrl}?projection=${projection}` : fullUrl;
    
        const response = await this._requestAdapter.getRequest().get(fullUrl);
        if(response.status === 404) { return undefined; }
        if(!response.ok){ throw response; }
        
        const result = (await response.json()) as T;
        return apiHelper.injectIds(result);
    }
    
    public async setAddress(returnType: Contact, child: Address) {
        // eslint-disable-next-line no-throw-literal
        if(!returnType._links) throw `Parent has no _links: ${returnType.id}`;
        // eslint-disable-next-line no-throw-literal
        if(!child._links) throw `Child has no _links: ${child.id}`;
        await this._requestAdapter.adaptAnyToOne(
            apiHelper.removeParamsFromNavigationHref(returnType._links.address),
            child._links.self.href
        );
    }
    
    public async readOwner(obj: ContactDto): Promise<User | undefined> {
        return this.readOwnerProjection<User>(obj);
    }
    
    public async readOwnerProjectionUserBaseProjection(obj: ContactDto): Promise<UserBaseProjection | undefined> {
        return this.readOwnerProjection<UserBaseProjection>(obj, "baseProjection");
    }
    
    public async readOwnerProjection<T extends ApiBase & { _links: ApiNavigationLinks }>(obj: ContactDto, projection?: string): Promise<T | undefined> {
        const hasProjection = (!!projection);
        let fullUrl = apiHelper.removeParamsFromNavigationHref(obj._links.owner);
        fullUrl = hasProjection ? `${fullUrl}?projection=${projection}` : fullUrl;
    
        const response = await this._requestAdapter.getRequest().get(fullUrl);
        if(response.status === 404) { return undefined; }
        if(!response.ok){ throw response; }
        
        const result = (await response.json()) as T;
        return apiHelper.injectIds(result);
    }
    
    public async setOwner(returnType: Contact, child: User) {
        // eslint-disable-next-line no-throw-literal
        if(!returnType._links) throw `Parent has no _links: ${returnType.id}`;
        // eslint-disable-next-line no-throw-literal
        if(!child._links) throw `Child has no _links: ${child.id}`;
        await this._requestAdapter.adaptAnyToOne(
            apiHelper.removeParamsFromNavigationHref(returnType._links.owner),
            child._links.self.href
        );
    }
  
    public async searchContactsByRegex<T extends Contact>(nameRegex: string, projection?: string, sort?: "id,ASC" | "id,DESC" | "firstName,ASC" | "firstName,DESC" | "lastName,ASC" | "lastName,DESC"): Promise<Items<T>> {
        const request = this._requestAdapter.getRequest();
        
        const parameters: {[key: string]: string} = {nameRegex};
            
        const url = stringHelper.appendParams("/search/contactsByRegex", parameters);
    
        const response = await request.get(url);
        const responseObj = ((await response.json()) as ApiHateoasObjectBase<T[]>);
        
        const elements = ((responseObj._embedded && responseObj._embedded.contacts) || []).map(item => (apiHelper.injectIds(item)));
        
        return {
            items: elements,
            _links: responseObj._links
        };
    }
    
    public async searchFindByNameContaining<T extends Contact>(name: string, projection?: string, page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "firstName,ASC" | "firstName,DESC" | "lastName,ASC" | "lastName,DESC"): Promise<PagedItems<T>> {
        const request = this._requestAdapter.getRequest();
        
        const parameters: {[key: string]: string} = {name};
        if (page !== undefined) {
            parameters["page"] = `${page}`;
        }
        if (size !== undefined) {
            parameters["size"] = `${size}`;
        }
            
        const url = stringHelper.appendParams("/contacts/search/findByNameContaining", parameters);
    
        const response = await request.get(url);
        const responseObj = ((await response.json()) as ApiHateoasObjectReadMultiple<T[]>);
        
                    const elements = ((responseObj._embedded && responseObj._embedded.contacts) || []).map(item => (apiHelper.injectIds(item)));
                
                    return {
                        items: elements,
                        _links: responseObj._links
        , page: responseObj.page
                    };
    }
    
    public async searchFindByNames<T extends Contact>(firstName: string, lastName: string, projection?: string, sort?: "id,ASC" | "id,DESC" | "firstName,ASC" | "firstName,DESC" | "lastName,ASC" | "lastName,DESC"): Promise<T | undefined> {
        const request = this._requestAdapter.getRequest();
        
        const parameters: {[key: string]: string} = {firstName, lastName};
            
        const url = stringHelper.appendParams("/contacts/search/findByNames", parameters);
    
        const response = await request.get(url);
        const responseObj = ((await response.json()) as T);
        
        return responseObj;
    }
   
    public async postCreateOrUpdate(userName: string, firstName: string, lastName: string, number: string, street: string, zip: string, city: string, country: string): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/contacts/createOrUpdate`;
        
        const params = {userName, firstName, lastName, number, street, zip, city, country};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
}

export class TestEntityClient extends BaseClient<ApiClient, TestEntityBase> {

    constructor(apiClient: ApiClient, requestAdapter?: RequestAdapter){
        super("/testEntities", "testEntities", apiClient, requestAdapter);
        this.readOne = this.readOne.bind(this);
        this.readProjection = this.readProjection.bind(this);
        
    }
  
    public static build(base: Partial<TestEntityBase> = {}): TestEntityBase {
        return {
            booleanTrue: base.booleanTrue !== undefined ? base.booleanTrue : false,
            date2000_6_12: base.date2000_6_12 !== undefined ? base.date2000_6_12 : "1970-01-01",
            dateTime2000_1_1_12_30: base.dateTime2000_1_1_12_30 !== undefined ? base.dateTime2000_1_1_12_30 : "1970-01-01T00:00:00",
            int32: base.int32 !== undefined ? base.int32 : 0,
            intMinusBillion: base.intMinusBillion !== undefined ? base.intMinusBillion : 0,
            long64: base.long64 !== undefined ? base.long64 : 0,
            optionalIntBillion: base.optionalIntBillion !== undefined ? base.optionalIntBillion : 0,
            optionalIntNull: base.optionalIntNull !== undefined ? base.optionalIntNull : 0,
            stringText: base.stringText !== undefined ? base.stringText : ""
        }
    }
  
    public async readProjectionsTestEntityBaseProjection(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "booleanTrue,ASC" | "booleanTrue,DESC" | "date2000_6_12,ASC" | "date2000_6_12,DESC" | "int32,ASC" | "int32,DESC" | "intMinusBillion,ASC" | "intMinusBillion,DESC" | "long64,ASC" | "long64,DESC" | "stringText,ASC" | "stringText,DESC") : Promise<PagedItems<TestEntityBaseProjection>> {
        return this.readProjections<TestEntityBaseProjection>("baseProjection", page, size, sort);
    }
            
    public async readProjectionTestEntityBaseProjection(id: number): Promise<TestEntityBaseProjection| undefined> {
        return this.readProjection<TestEntityBaseProjection>(id, "baseProjection");
    }
    
    public async readAll(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "booleanTrue,ASC" | "booleanTrue,DESC" | "date2000_6_12,ASC" | "date2000_6_12,DESC" | "int32,ASC" | "int32,DESC" | "intMinusBillion,ASC" | "intMinusBillion,DESC" | "long64,ASC" | "long64,DESC" | "stringText,ASC" | "stringText,DESC") : Promise<PagedItems<TestEntity>> {
        return await this.readProjections<TestEntity>(undefined, page, size, sort);
    }
  
    public toDto(obj: TestEntity): TestEntityDto {
        return obj;
    }

    public toBase<T extends TestEntityBase>(obj: T): TestEntityBase {
        return {
            booleanTrue: obj.booleanTrue,
            date2000_6_12: obj.date2000_6_12,
            dateTime2000_1_1_12_30: obj.dateTime2000_1_1_12_30,
            int32: obj.int32,
            intMinusBillion: obj.intMinusBillion,
            long64: obj.long64,
            optionalIntBillion: obj.optionalIntBillion,
            optionalIntNull: obj.optionalIntNull,
            stringText: obj.stringText,
            
        };
    }
  
    
  
    
  
    
   
    public async postMixedCreateByInt32(int32: number, body: TestEntity, long64: number): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/testEntities/mixedCreate/${int32}`;
        
        const params = {long64};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body:JSON.stringify(body),
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
    
    public async postNoBodyCreateByInt32(int32: number, long64: number): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/testEntities/noBodyCreate/${int32}`;
        
        const params = {long64};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
    
    public async postNoPathVariableCreate(body: TestEntity, long64: number): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/testEntities/noPathVariableCreate`;
        
        const params = {long64};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body:JSON.stringify(body),
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
    
    public async postNoRequestParamCreateByInt32(int32: number, body: TestEntity): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/testEntities/noRequestParamCreate/${int32}`;
        
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body:JSON.stringify(body),
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
    
    public async postPathVariableCreateByInt32ByLong64CustomByIntMinusBillionByStringTextByBooleanTrueByDateCustom(int32: number, long64Custom: number, intMinusBillion: number, stringText: string, booleanTrue: boolean, dateCustom: string): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/testEntities/pathVariableCreate/${int32}/${long64Custom}/${intMinusBillion}/${stringText}/${booleanTrue}/${dateCustom}`;
        
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
    
    public async postRequestParamCreate(int32: number, long64Custom: number, intMinusBillion: number, stringText: string, booleanTrue: boolean, dateCustom: string, optionalIntNull?: number, optionalIntBillion?: number, dateTime2000_1_1_12_30?: string): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/testEntities/requestParamCreate`;
        
        const params = {int32, long64Custom, optionalIntNull, optionalIntBillion, intMinusBillion, stringText, booleanTrue, dateCustom, dateTime2000_1_1_12_30};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
    
    public async postRequestBodyCreate(body: TestEntity): Promise<void>  {
        const request = this._requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/testEntities/requestBodyCreate`;
        
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        
        const response = await request.fetch(
            url,
            {
                method: "POST",
                headers: {
                    "content-type": "application/json"
                },
                body:JSON.stringify(body),
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
}

export class UserClient extends BaseClient<ApiClient, UserBase> {

    constructor(apiClient: ApiClient, requestAdapter?: RequestAdapter){
        super("/users", "users", apiClient, requestAdapter);
        this.readOne = this.readOne.bind(this);
        this.readProjection = this.readProjection.bind(this);
        this.readContactsProjection = this.readContactsProjection.bind(this);
    }
  
    public static build(base: Partial<UserBase> = {}): UserBase {
        return {
            name: base.name !== undefined ? base.name : "",
            contacts: base.contacts !== undefined ? base.contacts : []
        }
    }
  
    public async readProjectionsUserBaseProjection(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "name,ASC" | "name,DESC") : Promise<PagedItems<UserBaseProjection>> {
        return this.readProjections<UserBaseProjection>("baseProjection", page, size, sort);
    }
            
    public async readProjectionUserBaseProjection(id: number): Promise<UserBaseProjection| undefined> {
        return this.readProjection<UserBaseProjection>(id, "baseProjection");
    }
    
    public async readAll(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "name,ASC" | "name,DESC") : Promise<PagedItems<User>> {
        return await this.readProjections<User>(undefined, page, size, sort);
    }
  
    public toDto(obj: User): UserDto {
        return obj;
    }

    public toBase<T extends UserBase>(obj: T): UserBase {
        return {
            name: obj.name,
            contacts: obj.contacts,
        };
    }
  
    public async deleteFromContacts(returnType: User, childToDelete: Contact) {
        await this._requestAdapter.getRequest().delete(`/users/${returnType.id}/contacts/${childToDelete.id}`);
    }
  
    public async readContacts(obj: UserDto): Promise<Contact[]> {
        return this.readContactsProjection<Contact>(obj);
    }
    
    public async readContactsProjectionContactBaseProjection(obj: UserDto): Promise<ContactBaseProjection[]> {
        return this.readContactsProjection<ContactBaseProjection>(obj, "baseProjection");
    }
    
    
    
    public async readContactsProjectionContactFull(obj: UserDto): Promise<ContactFull[]> {
        return this.readContactsProjection<ContactFull>(obj, "full");
    }
    
    public async readContactsProjection<T extends ApiBase & { _links: ApiNavigationLinks }>(obj: UserDto, projection?: string): Promise<T[]> {
        const hasProjection = (!!projection);
        let fullUrl = apiHelper.removeParamsFromNavigationHref(obj._links.contacts);
        fullUrl = hasProjection ? `${fullUrl}?projection=${projection}` : fullUrl;
    
        const response = await this._requestAdapter.getRequest().get(fullUrl);
        if(response.status === 404) { return []; }
        if(!response.ok){ throw response; }
        return (((await response.json()) as ApiHateoasObjectBase<T[]>)._embedded.contacts).map(item => (apiHelper.injectIds(item)));
    }
    
    public async setContacts(returnType: User, children: Contact[]) {
        // eslint-disable-next-line no-throw-literal
        if(!returnType._links) throw `Parent has no _links: ${returnType.id}`;
        await this._requestAdapter.adaptAnyToMany(
            apiHelper.removeParamsFromNavigationHref(returnType._links.contacts),
            children.map(c => {
                // eslint-disable-next-line no-throw-literal
                if(!c._links) throw `Child has no _links: ${c.id}`;
                return c._links.self.href;
            })
        )
    }
    
    public async addToContacts(returnType: User, childToAdd: Contact) {
        await this._requestAdapter.addToObj(childToAdd, returnType, "contacts");
    }
  
    public async searchFindUserByName<T extends User>(name: string, projection?: string, sort?: "id,ASC" | "id,DESC" | "name,ASC" | "name,DESC"): Promise<T | undefined> {
        const request = this._requestAdapter.getRequest();
        
        const parameters: {[key: string]: string} = {name};
            
        const url = stringHelper.appendParams("/users/search/findUserByName", parameters);
    
        const response = await request.get(url);
        const responseObj = ((await response.json()) as T);
        
        return responseObj;
    }
   
    
}