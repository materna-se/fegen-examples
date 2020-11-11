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
import {action, observable} from "mobx";
import {Address, ContactNew} from "web-api";
import apiClient from "./apiClient";
import mainModel from "./mainModel";


class UpsertContactModel {

    @observable
    contact: ContactNew = {
        owner: null,
        firstName: "",
        lastName: "",
        number: "",
        address: {
            street: "",
            zip: "",
            city: "",
            country: ""
        } as Address
    };

    setContactField<K extends keyof ContactNew>(key: K) {
        return action("Set contact field", (value: ContactNew[K]) => {
            this.contact[key] = value;
        })
    }

    setAddressField<K extends keyof Address>(key: K) {
        return action("Set address field", (value: Address[K]) => {
            this.contact.address!![key] = value;
        });
    }

    save() {
        apiClient.customEndpointControllerClient.createOrUpdateContact(
            mainModel.loggedInUser!!.name,
            this.contact.firstName,
            this.contact.lastName,
            this.contact.number || "",
            this.contact.address?.street || "",
            this.contact.address?.zip || "",
            this.contact.address?.city || "",
            this.contact.address?.country || ""
        ).catch(e => console.error("Upserting contact failed", e));
    }
}

export default new UpsertContactModel();