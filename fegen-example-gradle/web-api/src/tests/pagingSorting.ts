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
import {apiClient, setupTest} from "./util";
import {expect} from "chai";
import * as assert from "assert";

describe("Paging and Sorting", () => {

    beforeEach(setupTest);

    const pageSize = 5;

    function slicePage<T>(items: T[], pageNumber: number): T[] {
        const start = pageNumber * pageSize;
        const end = Math.min((pageNumber + 1) * pageSize, items.length);
        return items.slice(start, end)
    }

    function expectSortedBy<T, K extends keyof T>(list: T[], key: K, reverse: boolean = false) {
        let lastItem: T | null = null;
        for (const item of list) {
            if (lastItem != null) {
                const value = item[key];
                const lastValue: T[K] = lastItem[key];
                const orderCorrect = reverse ? lastValue >= value : lastValue <= value;
                if (!orderCorrect) {
                    assert.fail(`Expected list to be sorted by ${key}, but \n${JSON.stringify(lastItem, undefined, 2)} comes before ${JSON.stringify(item, undefined, 2)}`);
                }
            }
            lastItem = item;
        }
    }

    it("fetches first page", async () => {
        const page = 0;
        const response = await apiClient.contactClient.readAll(page, pageSize);

        expect(response.page.number).to.equal(page);
        expect(response.page.size).to.equal(pageSize);
        expect(response.items.length).to.equal(pageSize);
        const slice = slicePage((await apiClient.contactClient.readAll()).items, page);
        expect(response.items).to.deep.equal(slice);
    });

    it("fetches second page", async () => {
        const page = 1;
        const response = await apiClient.contactClient.readAll(page, pageSize);

        const allContacts = await apiClient.contactClient.readAll();
        const expectedSize = Math.min(pageSize, allContacts.page.totalElements - page * pageSize);
        expect(response.page.number).to.equal(page);
        expect(response.page.size).to.equal(pageSize);
        expect(response.items.length).to.equal(expectedSize);
        const slice = slicePage(allContacts.items, page);
        expect(response.items).to.deep.equal(slice);
    });

    it("fetches sorted", async () => {
        const sorted = await apiClient.contactClient.readAll(undefined, undefined, "lastName,ASC");

        expect(sorted.items).to.not.have.length(0);
        expect(sorted.items).to.have.length(sorted.page.totalElements);
        expectSortedBy(sorted.items, "lastName");
    });

    it("fetches reverse sorted", async () => {
        const sorted = await apiClient.contactClient.readAll(undefined, undefined, "firstName,DESC");

        expect(sorted.items).to.not.have.length(0);
        expect(sorted.items).to.have.length(sorted.page.totalElements);
        expectSortedBy(sorted.items, "firstName", true);
    });

    it("fetches sorted page", async () => {
        const page = 1;
        const response = await apiClient.contactClient.readAll(page, pageSize, "lastName,ASC");

        const allContactsSorted = await apiClient.contactClient.readAll(undefined, undefined, "lastName,ASC");
        const expectedSize = Math.min(pageSize, allContactsSorted.page.totalElements - page * pageSize);
        expect(response.page.number).to.equal(page);
        expect(response.page.size).to.equal(pageSize);
        expect(response.items).to.have.length(expectedSize);
        const slice = slicePage(allContactsSorted.items, page);
        expect(response.items).to.deep.equal(slice);
    });

    it("fetches reverse sorted page", async () => {
        const page = 1;
        const response = await apiClient.contactClient.readAll(page, pageSize, "firstName,DESC");

        const allContactsSorted = await apiClient.contactClient.readAll(undefined, undefined, "firstName,DESC");
        const expectedSize = Math.min(pageSize, allContactsSorted.page.totalElements - page * pageSize);
        expect(response.page.number).to.equal(page);
        expect(response.page.size).to.equal(pageSize);
        expect(response.items).to.have.length(expectedSize);
        const slice = slicePage(allContactsSorted.items, page);
        expect(response.items).to.deep.equal(slice);
    });
});