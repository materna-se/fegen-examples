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
import {expect} from 'chai';
import {ContactFull} from "../Entities";

describe("Projection", () => {

    before(setupFetch);
    beforeEach(setupTest);

    it("fetches projections", async () => {
        const contact = await fetchWithAddress();

        expect(contact).to.exist;
        expect(contact?.address).to.exist;
        expect(contact?.address?.id).to.be.at.least(0);
    });

    it("fetches single projection", async () => {
        const withAddress = await fetchWithAddress();
        const contact = await apiClient.contactClient.readProjectionContactFull(withAddress.id);

        expect(contact).to.exist;
        expect(contact?.address).to.exist;
        expect(contact?.address?.id).to.be.at.least(0);
    });

    it("updates using projections", async () => {
        const withAddress = await fetchWithAddress();
        const updated = {
            ...withAddress,
            firstName: "NewFirstName"
        };
        const updateResult = await apiClient.contactClient.update(updated);
        const fetchedUpdated = await apiClient.contactClient.readProjectionContactFull(withAddress.id);

        expect(updated).to.deep.include(updateResult);
        expect(fetchedUpdated).to.deep.eq(updated);
    });

    async function fetchWithAddress(): Promise<ContactFull> {
        const fullContacts = await apiClient.contactClient.readProjectionsContactFull();
        return fullContacts.items.find(c => c.firstName == "With" && c.lastName == "Address")!!;
    }
});
