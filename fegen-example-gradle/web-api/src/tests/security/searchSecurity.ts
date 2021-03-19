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
import { expect } from "chai";
import {apiClient, setupTest} from "../util";
import {login} from "./util";


describe("Search Security", () => {

    beforeEach(setupTest);

    it("allows unsecured repository search for anonymous", async () => {
        expect(await apiClient.contactClient.isSearchFindByNamesAllowed()).to.eq(true);
        expect(await apiClient.contactClient.isSearchFindByNameContainingAllowed()).to.eq(true);
    });

    it("forbids secured repository search for anonymous", async () => {
        expect(await apiClient.primitiveTestEntityClient.isSearchFindByInt32Allowed()).to.eq(false);
    });

    it("forbids secured repository search for reader", async () => {
        await login("reader", "pwd");
        expect(await apiClient.primitiveTestEntityClient.isSearchFindByInt32Allowed()).to.eq(false);
    });

    it("allows secured repository search for admin", async () => {
        await login("admin", "pwd");
        expect(await apiClient.primitiveTestEntityClient.isSearchFindByInt32Allowed()).to.eq(true);
    });

    it("allows unsecured custom search for anonymous", async () => {
        expect(await apiClient.contactClient.isSearchContactsByRegexAllowed()).to.eq(true);
    });

    it("forbids secured custom search for anonymous", async () => {
        expect(await apiClient.contactClient.isSearchSecuredContactsByRegexAllowed()).to.eq(false);
    });

    it("forbids secured custom search for reader", async () => {
        await login("reader", "pwd");
        expect(await apiClient.contactClient.isSearchSecuredContactsByRegexAllowed()).to.eq(false);
    });

    it("allows secured custom search for admin", async () => {
        await login("admin", "pwd");
        expect(await apiClient.contactClient.isSearchSecuredContactsByRegexAllowed()).to.eq(true);
    });

})