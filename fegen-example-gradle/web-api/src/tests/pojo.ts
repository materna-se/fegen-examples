import {apiClient, setupFetch, setupTest} from "./util";
import {ComplexTestPojo, CreateRequest, PrimitiveTestPojo, RecursiveTestPojo} from "../Entities";
import {expect} from "chai";


describe("Pojo", () => {

    before(setupFetch);
    beforeEach(setupTest);

    const pojos: PrimitiveTestPojo[] = [
        {
            string: "test",
            number: 42,
            boolean: true
        }
    ];

    const pojo: ComplexTestPojo = {
        pojos: pojos,
    };

    it("call with pojo as request body", async () => {
        const result = await apiClient.testRestControllerClient.pojoAsBodyAndReturnValue(pojo);

        expect(result).to.be.not.empty;
        expect(result.pojos).to.deep.include.members(pojos);
    });

    it("call with pojo as request body and list return value", async () => {
        const result = await apiClient.testRestControllerClient.pojoAsBodyAndListReturnValue(pojo);

        expect(result).to.be.not.empty;
        expect(result.length).to.equal(2);
    });

    it("call with pojo as response", async () => {
        const result = await apiClient.testRestControllerClient.pojosAsReturnValue();
        expect(result).to.be.not.empty;
        expect(result.length).to.equal(2);
    });

    it("call with pojo list as response", async () => {
        const result = await apiClient.testRestControllerClient.pojoListAsBody(pojos);

        expect(result).to.be.not.empty;
        expect(result.length).to.equal(1);
    });

    it("calls custom endpoint to create contact using pojo as body param", async () => {
        const request: CreateRequest = {
            userName: "UserOne",
            firstName: "firstName",
            lastName: "lastName",
            number: "",
            street: "street",
            zip: "12345",
            city: "city",
            country: "country"
        };
        await apiClient.customEndpointControllerClient.createContact(request);
    });

    it("can use recursive pojo as body and return type", async () => {
        const request: RecursiveTestPojo = {
            recursive: {
                recursive: null
            }
        }
        const result = await apiClient.testRestControllerClient.recursivePojo(request);
        expect(result).to.deep.equal({
            recursive: {
                recursive: {
                    recursive: null
                }
            }
        });
    })
});