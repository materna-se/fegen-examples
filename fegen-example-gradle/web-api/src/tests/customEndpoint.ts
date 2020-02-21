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
import { expect } from "chai";
import {PrimitiveTestEntityBase} from "../Entities";


describe("Custom endpoint", () => {

    before(setupFetch);
    beforeEach(setupTest);

    const customEntity: PrimitiveTestEntityBase = {
        booleanTrue: false,
        date2000_6_12: new Date(1900, 4, 8, 0, 0, 0, 0).toISOString().substring(0, 10),
        dateTime2000_1_1_12_30: new Date(1950, 12, 31, 23, 59, 0, 0).toISOString().substring(0, 19),
        int32: 98498651,
        intMinusBillion : 0,
        long64: -6515164854,
        optionalIntBillion: null,
        optionalIntNull: -51651964,
        stringText: "27"
    };

    it("calls custom endpoint", async () => {
        await apiClient.contactClient.postCreateOrUpdate(
            "UserOne",
            "firstName",
            "lastName",
            "",
            "street",
            "12345",
            "city",
            "country"
        );
    });

    it("calls with path variables", async () => {
        const date = new Date(1234, 7, 13, 0, 0, 0, 0).toISOString().substring(0, 10);
        const result = await apiClient.primitiveTestEntityClient.postPathVariableCreateByInt32ByLong64CustomByIntMinusBillionByStringTextByBooleanTrueByDateCustom(
            1, 12, -123, "fghtejte", false, date
        );

        expect(result.int32).to.eq(1);
        expect(result.long64).to.eq(12);
        expect(result.intMinusBillion).to.eq(-123);
        expect(result.stringText).to.eq("fghtejte");
        expect(result.booleanTrue).to.eq(false);
        expect(result.date2000_6_12).to.eq(date);
    });

    it("calls with request parameters", async () => {
        const date = new Date(2003, 7, 3, 0, 0, 0, 0).toISOString().substring(0, 10);
        const result = await apiClient.primitiveTestEntityClient.postRequestParamCreate(
            1, 12, -123, "grshteh", false, date, -541651664);

        expect(result.int32).to.eq(1);
        expect(result.long64).to.eq(12);
        expect(result.intMinusBillion).to.eq(-123);
        expect(result.stringText).to.eq("grshteh");
        expect(result.booleanTrue).to.eq(false);
        expect(result.date2000_6_12).to.eq(date);
        expect(result.optionalIntNull).to.eq(-541651664);
        expect(result.optionalIntBillion).to.eq(null);
        expect(result.dateTime2000_1_1_12_30).to.eq(null)
    });

    it("calls with request body", async () => {
        const result = await apiClient.primitiveTestEntityClient.postRequestBodyCreate(customEntity);

        expect(result).to.contain(customEntity);
    });

    it("calls with path variables and request parameters", async () => {
        const result = await apiClient.primitiveTestEntityClient.postNoBodyCreateByInt32(684, 848);

        expect(result.int32).to.eq(684);
        expect(result.long64).to.eq(848);
    });

    it("calls with path variables and request body", async () => {
        const result = await apiClient.primitiveTestEntityClient.postNoRequestParamCreateByInt32(789, customEntity);

        expect(result).to.contain({ ...customEntity, int32: 789 });
    });

    it("call with request parameters and request body", async () => {
        const result = await apiClient.primitiveTestEntityClient.postNoPathVariableCreate(customEntity, -65446545);

        expect(result).to.contain({ ...customEntity, long64: -65446545 })
    });

    it("receives lists", async () => {
        const result = await apiClient.primitiveTestEntityClient.getReturnList();
    });
});
