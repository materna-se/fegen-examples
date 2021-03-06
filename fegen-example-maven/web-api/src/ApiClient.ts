// This file has been generated by FeGen based on a spring application. Do not edit it.

import {
    BaseClient, RequestAdapter,
    ApiHateoasObjectBase, ApiHateoasObjectReadMultiple, Items, PagedItems, ApiNavigationLinks, ApiBase,
    apiHelper, stringHelper
} from 'fegen-runtime';
import { UserBase, UserDto, User } from './Entities'
import {  } from './Entities'
import { UserBaseProjection } from './Entities'

export class ApiClient {
    public readonly userClient: UserClient;

    private readonly baseUrl: string;

    constructor(requestAdapter?: RequestAdapter, baseUrl?: string) {
        this.baseUrl = baseUrl || "";
        const adapter = requestAdapter || new RequestAdapter(this.baseUrl);
        this.userClient = new UserClient(this, adapter);
    }
}

export class UserClient extends BaseClient<ApiClient, UserBase> {

    constructor(apiClient: ApiClient, requestAdapter?: RequestAdapter){
        super("/users", "users", apiClient, requestAdapter);
        this.readOne = this.readOne.bind(this);
        this.readProjection = this.readProjection.bind(this);
        
    }
  
    public build(): UserBase {
        return {
            street: "",
            
        }
    }
  
    public async readProjectionsUserBaseProjection(page?: number, size?: number, sort?: "name,ASC" | "name,DESC" | "id,ASC" | "id,DESC" | "street,ASC" | "street,DESC") : Promise<PagedItems<UserBaseProjection>> {
        return this.readProjections<UserBaseProjection>("baseProjection", page, size, sort);
    }
            
    public async readProjectionUserBaseProjection(id: number): Promise<UserBaseProjection| undefined> {
        return this.readProjection<UserBaseProjection>(id, "baseProjection");
    }
    
    public async readAll(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "street,ASC" | "street,DESC") : Promise<PagedItems<User>> {
        return await this.readProjections<User>(undefined, page, size, sort);
    }
  
    public toDto(obj: User): UserDto {
        return obj;
    }

    public toBase<T extends UserBase>(obj: T): UserBase {
        return {
            street: obj.street,
            
        };
    }
  
    
  
    
  
    
   
    
}