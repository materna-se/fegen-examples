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
import {autorun, computed, observable, runInAction} from "mobx";
import apiClient from "./apiClient";
import userModel from "./mainModel";
import mainModel from "./mainModel";
import {Contact} from "web-api";


class UserContactsModel {

    constructor() {
        autorun(async () => {
            if (mainModel.loggedInUser) {
                const contacts = await apiClient.userClient.readContacts(mainModel.loggedInUser);
                runInAction("Load contacts for user", () => {
                    this.contacts = contacts;
                });
            } else {
                this.contacts = [];
            }
        })
    }

    @observable
    contacts: Contact[] = [];

    @computed
    get username() {
        return mainModel.loggedInUser?.name;
    }

    async createContact(firstName: string, lastName: string) {
        const contact = await apiClient.contactClient.create({firstName, lastName, owner: userModel.loggedInUser!!});
        runInAction("Add contact", () => {
            this.contacts.push(contact);
        });
    }

    loadContact(contact: Contact) {
        mainModel.selectContact(contact)
    }
}

export default new UserContactsModel();
