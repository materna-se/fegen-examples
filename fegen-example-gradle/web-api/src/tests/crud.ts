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
import {expect} from "chai";
import {apiClient, setupTest} from "./util";
import {UserNew} from "../Entities";

describe("CRUD", () => {

    beforeEach(setupTest);

    it("reads all", async () => {
        const allUsers = await apiClient.userClient.readAll();

        expect(allUsers.items).not.to.be.empty
    });

    it("creates", async () => {
        const usersBefore = await apiClient.userClient.readAll();
        const userBase: UserNew = {
            name: "user_name",
            contacts: []
        };

        const newUser = await apiClient.userClient.create(userBase);

        expect(newUser).to.exist;
        expect(newUser).to.haveOwnProperty("name", userBase.name);
        expect(newUser).to.haveOwnProperty("id").that.is.greaterThan(0);
        const usersAfter = await apiClient.userClient.readAll();
        expect(usersAfter.page.totalElements).to.equal(usersBefore.page.totalElements + 1);
    });

    it("updates", async () => {
        const user = (await apiClient.userClient.readAll()).items[0];

        const changeRequest = {...user, name: "newName"};
        const changedUser = await apiClient.userClient.update(changeRequest);

        expect(changedUser).to.exist;
        expect(changedUser).to.haveOwnProperty("name", changeRequest.name);
        expect(changedUser).to.haveOwnProperty("id", changeRequest.id);
    });

    it("deletes", async () => {
        const usersBefore = await apiClient.userClient.readAll();
        const users = await apiClient.userClient.readAll();
        const user = users.items.find(u => u.name === "deletableUser")!!;

        await apiClient.userClient.delete(user);

        const usersAfter = await apiClient.userClient.readAll();
        expect(usersAfter.page.totalElements).to.equal(usersBefore.page.totalElements - 1);
    });

    //base path overridden
    it("can read all addresses", async () => {
        const allAddresses = await apiClient.addressClient.readAll();

        expect(allAddresses.items).not.to.be.empty
    });

});
