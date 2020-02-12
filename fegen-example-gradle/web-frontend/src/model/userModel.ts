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
import {User} from "web-api";
import mainModel from "./mainModel";
import {computed, observable, runInAction} from "mobx";
import apiClient from "./apiClient";


class UserModel {

    @observable users: User[] = [];

    @computed
    get loggedInUser() {
        return mainModel.loggedInUser;
    }

    constructor() {
        this.loadUsers();
    }

    async loadUsers() {
        const users = await apiClient.userClient.readAll();
        runInAction("Load users", () => {
            this.users = users.items;
        })
    }

    selectUser(user?: User) {
        mainModel.selectUser(user);
    }

    async createUser(name: string) {
        const user = await apiClient.userClient.create({name, contacts: []});
        runInAction("Create user", () => {
            this.users.push(user);
        })
    }
}

export default new UserModel();