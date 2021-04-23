import {Contact, CreateRequest} from "../Entities";
import {RequestAdapter, stringHelper, isEndpointCallAllowed} from "@materna-se/fegen-runtime";

export class CustomEndpointControllerClient {

    private readonly requestAdapter: RequestAdapter;
    
    constructor(requestAdapter: RequestAdapter) {
        this.requestAdapter = requestAdapter;
    }
    
    public async createContact(body: CreateRequest): Promise<Contact>  {
        const request = this.requestAdapter.fetchAdapter;
    
        const baseUrl = `/api/custom/contacts/create`;
    
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
        
        
        const responseObj = (await response.json()) as Contact;
        return responseObj;
        
        
    }
    
    public isCreateContactAllowed(): Promise<boolean> {
        const url = `/api/custom/contacts/create`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
    
    public async createOrUpdateContact(userName: string, firstName: string, lastName: string, number: string, street: string, zip: string, city: string, country: string): Promise<Contact>  {
        const request = this.requestAdapter.fetchAdapter;
    
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
        
        
        const responseObj = (await response.json()) as Contact;
        return responseObj;
        
        
    }
    
    public isCreateOrUpdateContactAllowed(): Promise<boolean> {
        const url = `/api/custom/contacts/createOrUpdate`;
        return isEndpointCallAllowed(this.requestAdapter.fetchAdapter, "//api", "POST", url);
    }
}