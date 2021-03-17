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
import {expect} from "chai";
import * as assert from "assert";
import {Contact} from "../Entities";


describe("Custom search", () => {

    before(setupFetch);
    beforeEach(setupTest);

    function checkSearchCriteria(contacts: Contact[], regexStr: string) {
        for (const c of contacts) {
            const regex = new RegExp(regexStr);
            if (![c.firstName, c.lastName].find(n => regex.test(n))) {
                assert.fail(`${c.firstName} or ${c.lastName} should contain ${regexStr}`)
            }
        }
    }

    it("calls custom searches", async () => {
        const regexStr = "n.?$";
        const results = (await apiClient.contactClient.searchContactsByRegex(regexStr));

        expect(results.items).to.not.be.empty;
        checkSearchCriteria(results.items, regexStr);
    });

    it("ignores method", () => {
        expect(apiClient.contactClient).to.have.property("searchContactsByRegex");
        expect(apiClient.contactClient).to.not.have.property("searchIgnoredContactsByRegex");
    });

    it("ignores class", () => {
        expect(apiClient.contactClient).to.not.have.property("findAllByTextIsStartingWith");
        expect(apiClient.contactClient).to.not.have.property("findAllByTextIsEndingWith");
    });
});