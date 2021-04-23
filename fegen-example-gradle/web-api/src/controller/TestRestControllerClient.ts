import {CyclicTestPojoA, PrimitiveTestEntity, PrimitiveTestEntityNew, ComplexTestPojo, PrimitiveTestPojo, RecursiveTestPojo} from "../Entities";
import {RequestAdapter, stringHelper, isEndpointCallAllowed, Items, ApiHateoasObjectBase, apiHelper, PagedItems, ApiHateoasObjectReadMultiple} from "@materna-se/fegen-runtime";

export class TestRestControllerClient {

    private readonly requestAdapter: RequestAdapter;
    
    constructor(requestAdapter: RequestAdapter) {
        this.requestAdapter = requestAdapter;
    }
    
    public async cyclicPojo(body: CyclicTestPojoA): Promise<CyclicTestPojoA>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/cyclicPojo`;
    
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
    
    public isCyclicPojoAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/cyclicPojo`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async mixed(int32: number, body: PrimitiveTestEntityNew, long64: number): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/mixedCreate/${int32}`;
    
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
    
    public isMixedAllowed(int32: number): Promise<boolean> {
        const url = `/api/custom/testController/mixedCreate/${int32}`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async noBody(int32: number, long64: number): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/noBodyCreate/${int32}`;
    
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
    
    public isNoBodyAllowed(int32: number): Promise<boolean> {
        const url = `/api/custom/testController/noBodyCreate/${int32}`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async noPathVariable(body: PrimitiveTestEntityNew, long64: number): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/noPathVariableCreate`;
    
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
    
    public isNoPathVariableAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/noPathVariableCreate`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async noRequestParam(int32: number, body: PrimitiveTestEntityNew): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/noRequestParamCreate/${int32}`;
    
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
    
    public isNoRequestParamAllowed(int32: number): Promise<boolean> {
        const url = `/api/custom/testController/noRequestParamCreate/${int32}`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async pathVariable(int32: number, long64Custom: number, intMinusBillion: number, stringText: string, booleanTrue: boolean, dateCustom: string): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/pathVariableCreate/${int32}/${long64Custom}/${intMinusBillion}/${stringText}/${booleanTrue}/${dateCustom}`;
    
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
    
    public isPathVariableAllowed(int32: number, long64Custom: number, intMinusBillion: number, stringText: string, booleanTrue: boolean, dateCustom: string): Promise<boolean> {
        const url = `/api/custom/testController/pathVariableCreate/${int32}/${long64Custom}/${intMinusBillion}/${stringText}/${booleanTrue}/${dateCustom}`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async pojoAsBodyAndListReturnValue(body: ComplexTestPojo): Promise<ComplexTestPojo[]>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/pojoAsBodyAndListReturnValue`;
    
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
    
    public isPojoAsBodyAndListReturnValueAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/pojoAsBodyAndListReturnValue`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async pojoAsBodyAndReturnValue(body: ComplexTestPojo): Promise<ComplexTestPojo>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/pojoAsBodyAndSingleReturnValue`;
    
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
    
    public isPojoAsBodyAndReturnValueAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/pojoAsBodyAndSingleReturnValue`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async pojoListAsBody(body: PrimitiveTestPojo[]): Promise<PrimitiveTestPojo[]>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/pojoListAsBody`;
    
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
    
    public isPojoListAsBodyAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/pojoListAsBody`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async pojosAsReturnValue(): Promise<PrimitiveTestPojo[]>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/pojosAsReturnValue`;
    
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
    
    public isPojosAsReturnValueAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/pojosAsReturnValue`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async recursivePojo(body: RecursiveTestPojo): Promise<RecursiveTestPojo>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/recursivePojo`;
    
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
    
    public isRecursivePojoAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/recursivePojo`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async requestParam(int32: number, long64Custom: number, intMinusBillion: number, stringText: string, booleanTrue: boolean, dateCustom: string, optionalIntNull?: number, optionalIntBillion?: number, dateTime2000_1_1_12_30?: string): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/requestParamCreate`;
    
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
    
    public isRequestParamAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/requestParamCreate`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async responseBody(body: PrimitiveTestEntityNew): Promise<PrimitiveTestEntity>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/requestBodyCreate`;
    
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
    
    public isResponseBodyAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/requestBodyCreate`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async returnBoolean(): Promise<boolean>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/returnBoolean`;
    
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
    
    public isReturnBooleanAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/returnBoolean`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async returnInteger(): Promise<number>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/returnInteger`;
    
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
    
    public isReturnIntegerAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/returnInteger`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async returnList(): Promise<Items<PrimitiveTestEntity>>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/returnList`;
    
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
    
    public isReturnListAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/returnList`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async returnPaged(page?: number, size?: number, sort?: "id,ASC" | "id,DESC" | "booleanTrue,ASC" | "booleanTrue,DESC" | "date2000_6_12,ASC" | "date2000_6_12,DESC" | "int32,ASC" | "int32,DESC" | "intMinusBillion,ASC" | "intMinusBillion,DESC" | "long64,ASC" | "long64,DESC" | "stringText,ASC" | "stringText,DESC"): Promise<PagedItems<PrimitiveTestEntity>>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/returnPaged`;
    
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
            _links: responseObj._links,
            page: responseObj.page
        };
        
        
    }
    
    public isReturnPagedAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/returnPaged`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async returnString(): Promise<string>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/returnString`;
    
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
    
    public isReturnStringAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/returnString`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async returnStringList(): Promise<Items<string>>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/returnStringList`;
    
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
    
    public isReturnStringListAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/returnStringList`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async returnVoid(): Promise<void>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/returnVoid`;
    
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
    
    public isReturnVoidAllowed(): Promise<boolean> {
        const url = `/api/custom/testController/returnVoid`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "GET", url);
    }
    
    public async securedEndpoint(pathParam: string, queryParam: number): Promise<string>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/testController/securedEndpoint/${pathParam}`;
    
        const params = {queryParam};
    
        const url = stringHelper.appendParams(baseUrl, params);
    
        const response = await request.fetch(
            url,
            {
                method: "PUT"
            },
            true);
    
        if(!response.ok) {
            throw response;
        }
        
        
        return (await response.json()) as string;
        
        
    }
    
    public isSecuredEndpointAllowed(pathParam: string): Promise<boolean> {
        const url = `/api/custom/testController/securedEndpoint/${pathParam}`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "PUT", url);
    }
}