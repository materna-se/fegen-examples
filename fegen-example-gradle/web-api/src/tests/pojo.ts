/**
 * Copyright 2020 Materna Information & Communications SE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import {apiClient, setupFetch, setupTest} from "./util";
import {ComplexTestPojo, CreateRequest, CyclicTestPojoA, PrimitiveTestPojo, RecursiveTestPojo} from "../Entities";
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
    });

    it("can use cyclic pojo as body and return type", async () => {
       const request: CyclicTestPojoA = {
           b: {
               a: null
           }
       };
       const result = await apiClient.testRestControllerClient.cyclicPojo(request);
       expect(result).to.deep.equal({
          b: {
              a: request
          }
       });
    });
});