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
import {TestEntityBase} from "../Entities";
import { expect } from "chai";


describe("Data types", () => {

    before(setupFetch);
    beforeEach(setupTest);

    const defaultEntity = {
        booleanTrue: true,
            date2000_6_12: "2000-06-12",
        dateTime2000_1_1_12_30: "2000-01-01T12:30:00",
        int32: 32,
        intMinusBillion: -1_000_000_000,
        long64: 64,
        optionalIntBillion: 1_000_000_000,
        optionalIntNull: null,
        stringText: "text"
    };

    it("can be read", async () => {
        const entity = (await apiClient.testEntityClient.readAll()).items[0];

        expect(entity).to.include(defaultEntity);
    });

    it("can be written", async () => {
        const entity: TestEntityBase = {
            booleanTrue: false,
            date2000_6_12: "1900-04-08",
            dateTime2000_1_1_12_30: "1950-12-31T23:59:00",
            int32: 1_000_000,
            intMinusBillion: 0,
            long64: -1_000_000_000_000_000,
            optionalIntBillion: null,
            optionalIntNull: 0,
            stringText: "27"
        };
        const createReturn = await apiClient.testEntityClient.create(entity);
        const testEntities = (await apiClient.testEntityClient.readAll()).items;

        expect(createReturn).to.exist;
        expect(createReturn).to.include(entity);
        expect(testEntities).to.deep.include(createReturn)
    });

    it("can use default values", async () => {
        const entity: Partial<TestEntityBase> = {};
        const createReturn = await apiClient.testEntityClient.create(entity);

        expect(createReturn).to.include(defaultEntity);
    });
});
