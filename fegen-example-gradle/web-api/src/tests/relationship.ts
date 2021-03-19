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
import {expect} from 'chai';
import {Contact, ContactNew, User} from "../Entities";

describe("Relationship", () => {

    beforeEach(setupTest);

    async function getUserOne(): Promise<User> {
        const users = await apiClient.userClient.readAll();
        return users.items.find(u => u.name === "UserOne")!!;
    }

    async function getContactWithAddress(): Promise<Contact> {
        return (await apiClient.contactClient.readAll()).items.find(u => u.firstName == "With" && u.lastName == "Address")!!;
    }

    async function getContactWithoutAddress(): Promise<Contact> {
        return (await apiClient.contactClient.readAll()).items.find(u => u.firstName == "Without" && u.lastName == "Address")!!;
    }

    async function createAddress() {
        const addressBase = {
            street: "ExampleStreet",
            zip: "12345",
            city: "ExampleCity",
            country: "ExampleCountry"
        };
        return await apiClient.addressClient.create(addressBase);
    }

    it("create referencing existing", async () => {
        const user = await apiClient.userClient.create({
            name: "CreatedUser",
            contacts: []
        });

        const contactToCreate: ContactNew = {
            firstName: "Created",
            lastName: "Contact",
            address: null,
            number: "123456789",
            owner: user
        };

        const createdContact = await apiClient.contactClient.create(contactToCreate);

        const contacts = await apiClient.userClient.readContacts(user);

        expect(contacts).to.have.length(1);
        const contact = contacts[0];
        expect(contact.firstName).to.equal(contactToCreate.firstName);
        expect(contact.lastName).to.equal(contactToCreate.lastName);
        expect(createdContact).to.deep.equal(contact);

        const owner = await apiClient.contactClient.readOwner(contact);

        expect(owner!!.id).to.equal(user.id);
        expect(owner!!.name).to.equal(user.name);
    });

    it("reads set of related", async () => {
        const userOne = await getUserOne();

        const contacts = await apiClient.userClient.readContacts(userOne);

        expect(contacts).to.not.have.length(0);
        for (const contact of contacts) {
            expect(contact.id).to.be.at.least(0);
        }
    });

    it("reads existing optional related", async () => {
        const contact = await getContactWithAddress();

        const address = await apiClient.contactClient.readAddress(contact);

        expect(address).to.exist;
    });

    it("reads absent optional related", async () => {
        const contact = await getContactWithoutAddress();

        const address = await apiClient.contactClient.readAddress(contact);

        expect(address).to.not.exist;
    });

    it("sets optional related", async () => {
        const address = await createAddress();
        const contact = await getContactWithoutAddress();

        await apiClient.contactClient.setAddress(contact, address);

        const savedAddress = await apiClient.contactClient.readAddress(contact);
        expect(savedAddress).to.exist;
        expect(savedAddress?.id).to.equal(address.id);
    });

    it("replaces optional related", async () => {
        const address = await createAddress();
        const contact = await getContactWithAddress();

        await apiClient.contactClient.setAddress(contact, address);

        const savedAddress = await apiClient.contactClient.readAddress(contact);
        expect(savedAddress).to.exist;
        expect(savedAddress?.id).to.equal(address.id);
    });

    it("removes relationship", async () => {
        const contact = await getContactWithAddress();
        const address = await apiClient.contactClient.readAddress(contact);

        await apiClient.contactClient.deleteFromAddress(contact, address!!);

        const savedAddess = await apiClient.contactClient.readAddress(contact);
        expect(savedAddess).to.not.exist;
    });

    it("adds related to set", async () => {
        const contact = await apiClient.contactClient.create({
            firstName: "Example",
            lastName: "Contact",
            address: null,
            number: null,
            owner: null
        });
        const user = await getUserOne();
        const contactsBefore =  await apiClient.userClient.readContacts(user);

        await apiClient.contactClient.setOwner(contact, user);

        const contactsAfter = await apiClient.userClient.readContacts(user);
        expect(contactsAfter).to.have.length(contactsBefore.length + 1);
        expect(contactsAfter).to.deep.contain(contact);
    });

    it("removes related from set", async () => {
        const user = await getUserOne();
        const contactsBefore = await apiClient.userClient.readContacts(user);
        const contactToRemove = contactsBefore[0];

        await apiClient.contactClient.deleteFromOwner(contactToRemove, user);

        const contactsAfter = await apiClient.userClient.readContacts(user);
        expect(contactsAfter).to.have.length(contactsBefore.length - 1);
        expect(contactsAfter).to.not.deep.contain(contactToRemove);
    });
});
