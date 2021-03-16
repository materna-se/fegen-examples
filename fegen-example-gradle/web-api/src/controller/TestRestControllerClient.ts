import {CyclicTestPojoA, PrimitiveTestEntity, PrimitiveTestEntityNew, ComplexTestPojo, PrimitiveTestPojo, RecursiveTestPojo} from "../Entities";
import {RequestAdapter, stringHelper, Items, ApiHateoasObjectBase, apiHelper, PagedItems, ApiHateoasObjectReadMultiple} from "@materna-se/fegen-runtime";

export class TestRestControllerClient {

    private readonly requestAdapter = new RequestAdapter();
    
    constructor(requestAdapter?: RequestAdapter) {
        if (requestAdapter) {
            this.requestAdapter = requestAdapter;
        }
    }
    
    public async cyclicPojo(body: CyclicTestPojoA): Promise<CyclicTestPojoA>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/cyclicPojo`;
    
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
        
        
        return (await response.json()) as CyclicTestPojoA;
        
        
    }
    
    public async mixed(int32: number, body: PrimitiveTestEntityNew, long64: number): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/mixedCreate/${int32}`;
    
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
        
        
        const responseObj = (await response.json()) as PrimitiveTestEntity;
        return responseObj;
        
        
    }
    
    public async noBody(int32: number, long64: number): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/noBodyCreate/${int32}`;
    
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
        
        
        const responseObj = (await response.json()) as PrimitiveTestEntity;
        return responseObj;
        
        
    }
    
    public async noPathVariable(body: PrimitiveTestEntityNew, long64: number): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/noPathVariableCreate`;
    
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
        
        
        const responseObj = (await response.json()) as PrimitiveTestEntity;
        return responseObj;
        
        
    }
    
    public async noRequestParam(int32: number, body: PrimitiveTestEntityNew): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/noRequestParamCreate/${int32}`;
    
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
        
        
        const responseObj = (await response.json()) as PrimitiveTestEntity;
        return responseObj;
        
        
    }
    
    public async pathVariable(int32: number, long64Custom: number, intMinusBillion: number, stringText: string, booleanTrue: boolean, dateCustom: string): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/pathVariableCreate/${int32}/${long64Custom}/${intMinusBillion}/${stringText}/${booleanTrue}/${dateCustom}`;
    
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
        
        
        const responseObj = (await response.json()) as PrimitiveTestEntity;
        return responseObj;
        
        
    }
    
    public async pojoAsBodyAndListReturnValue(body: ComplexTestPojo): Promise<ComplexTestPojo[]>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/pojoAsBodyAndListReturnValue`;
    
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
        
        
        return (await response.json()) as ComplexTestPojo[];
        
        
    }
    
    public async pojoAsBodyAndReturnValue(body: ComplexTestPojo): Promise<ComplexTestPojo>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/pojoAsBodyAndSingleReturnValue`;
    
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
        
        
        return (await response.json()) as ComplexTestPojo;
        
        
    }
    
    public async pojoListAsBody(body: PrimitiveTestPojo[]): Promise<PrimitiveTestPojo[]>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/pojoListAsBody`;
    
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
        
        
        return (await response.json()) as PrimitiveTestPojo[];
        
        
    }
    
    public async pojosAsReturnValue(): Promise<PrimitiveTestPojo[]>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/pojosAsReturnValue`;
    
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        return (await response.json()) as PrimitiveTestPojo[];
        
        
    }
    
    public async recursivePojo(body: RecursiveTestPojo): Promise<RecursiveTestPojo>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/recursivePojo`;
    
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
        
        
        return (await response.json()) as RecursiveTestPojo;
        
        
    }
    
    public async requestParam(int32: number, long64Custom: number, intMinusBillion: number, stringText: string, booleanTrue: boolean, dateCustom: string, optionalIntNull?: number, optionalIntBillion?: number, dateTime2000_1_1_12_30?: string): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/requestParamCreate`;
    
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
        
        
        const responseObj = (await response.json()) as PrimitiveTestEntity;
        return responseObj;
        
        
    }
    
    public async responseBody(body: PrimitiveTestEntityNew): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/requestBodyCreate`;
    
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
        
        
        const responseObj = (await response.json()) as PrimitiveTestEntity;
        return responseObj;
        
        
    }
    
    public async returnBoolean(): Promise<boolean>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/returnBoolean`;
    
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        return (await response.json()) as boolean;
        
        
    }
    
    public async returnInteger(): Promise<number>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/returnInteger`;
    
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        return (await response.json()) as number;
        
        
    }
    
    public async returnList(): Promise<Items<PrimitiveTestEntity>>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/returnList`;
    
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        const responseObj = (await response.json()) as ApiHateoasObjectBase<PrimitiveTestEntity[]>;
        const elements = ((responseObj._embedded && responseObj._embedded.primitiveTestEntities) || []).map(item => (apiHelper.injectIds(item)));
    
    return {
        items: elements,
        _links: responseObj._links
    };
        
        
    }
    
    public async returnPaged(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "booleanTrue,ASC" | "booleanTrue,DESC" | "date2000_6_12,ASC" | "date2000_6_12,DESC" | "int32,ASC" | "int32,DESC" | "intMinusBillion,ASC" | "intMinusBillion,DESC" | "long64,ASC" | "long64,DESC" | "stringText,ASC" | "stringText,DESC"): Promise<PagedItems<PrimitiveTestEntity>>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/returnPaged`;
    
        const params = {page, size, sort};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        const responseObj = (await response.json()) as ApiHateoasObjectReadMultiple<PrimitiveTestEntity[]>;
                    const elements = ((responseObj._embedded && responseObj._embedded.primitiveTestEntities) || []).map(item => (apiHelper.injectIds(item)));
            
                return {
                    items: elements,
                    _links: responseObj._links
    , page: responseObj.page
                };
        
        
    }
    
    public async returnString(): Promise<string>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/returnString`;
    
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        return (await response.json()) as string;
        
        
    }
    
    public async returnStringList(): Promise<Items<string>>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/returnStringList`;
    
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        return (await response.json()) as Items<string>;
        
        
    }
    
    public async returnVoid(): Promise<void>  {
        const request = this.requestAdapter.getRequest();
    
        const baseUrl = `/api/custom/primitiveTestEntities/returnVoid`;
    
        const params = {};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "GET"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
    }
}