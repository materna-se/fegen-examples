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
import {action, autorun, observable, runInAction} from "mobx";
import {Address, AddressBase, ContactFull, AddressClient} from "web-api";
import apiClient from "./apiClient";
import mainModel from "./mainModel";

class ContactDetailsModel {

    constructor() {
        autorun(async () => {
            if (mainModel.contact) {
                const contact = (await apiClient.contactClient.readProjectionContactFull(mainModel.contact.id))!!;
                this.setContactFull(contact);
            }
        })
    }

    @observable
    contact: ContactFull | null = null;

    @action
    setContactFull(contact: ContactFull) {
        this.contact = contact;
    }

    @action
    setNumber(number: string) {
        this.contact!!.number = number;
    }

    @action
    setAddressField<K extends keyof AddressBase>(key: K, value: Address[K]) {
        this.contact!!.address!![key] = value;
    }

    async updateContact() {
        await apiClient.contactClient.update(this.contact!!);
        if (this.contact!!.address) {
            await apiClient.addressClient.update(this.contact!!.address);
        }
    }

    async addAddress() {
        const contact = this.contact;
        if (!contact) {
            return;
        }
        const newAddress = AddressClient.build();
        const address = await apiClient.addressClient.create(newAddress);
        await apiClient.contactClient.setAddress(contact, address);
        runInAction("Add address", () => {
            contact.address = address;
        });
    }

    async deleteAddress() {
        const contact = this.contact;
        const address = contact?.address;
        if (contact && address) {
            await apiClient.contactClient.deleteFromAddress(contact, address);
            runInAction("Remove address", () => {
                contact.address = null;
            });
            await apiClient.addressClient.delete(address);
        }
    }
}

export default new ContactDetailsModel();