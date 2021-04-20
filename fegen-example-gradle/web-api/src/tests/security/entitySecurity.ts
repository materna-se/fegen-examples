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
import {apiClient, setupTest} from "../util";
import {expect} from "chai";
import {loggedIn} from "./util";

describe("Entity Security", () => {

    beforeEach(setupTest);

    it("returns all permissions for unsecured entity if anonymous", async () => {
        const allowedMethods = await apiClient.primitiveTestEntityClient.allowedMethods();
        expect(allowedMethods.readAll).to.eq(true);
        expect(allowedMethods.readOne).to.eq(true);
        expect(allowedMethods.create).to.eq(true);
        expect(allowedMethods.update).to.eq(true);
        expect(allowedMethods.remove).to.eq(true);
    });

    it("returns no permissions for secured entity if anonymous", async () => {
        const allowedMethods = await apiClient.securedEntityClient.allowedMethods();
        expect(allowedMethods.readAll).to.eq(false);
        expect(allowedMethods.readOne).to.eq(false);
        expect(allowedMethods.create).to.eq(false);
        expect(allowedMethods.update).to.eq(false);
        expect(allowedMethods.remove).to.eq(false);
    });

    it("returns read permissions for secured entity if reader", async () => {
        const loggedInClient = await loggedIn("reader", "pwd");
        const allowedMethods = await loggedInClient.securedEntityClient.allowedMethods();
        expect(allowedMethods.readAll).to.eq(true);
        expect(allowedMethods.readOne).to.eq(true);
        expect(allowedMethods.create).to.eq(false);
        expect(allowedMethods.update).to.eq(false);
        expect(allowedMethods.remove).to.eq(false);
    });

    it("returns create permissions for secured entity if writer", async () => {
        const loggedInClient = await loggedIn("writer", "pwd");
        const allowedMethods = await loggedInClient.securedEntityClient.allowedMethods();
        expect(allowedMethods.readAll).to.eq(true);
        expect(allowedMethods.readOne).to.eq(true);
        expect(allowedMethods.create).to.eq(true);
        expect(allowedMethods.update).to.eq(false);
        expect(allowedMethods.remove).to.eq(false);
    });

    it("returns all permissions for secured entity if admin", async () => {
        const loggedInClient = await loggedIn("admin", "pwd");
        const allowedMethods = await loggedInClient.securedEntityClient.allowedMethods();
        expect(allowedMethods.readAll).to.eq(true);
        expect(allowedMethods.readOne).to.eq(true);
        expect(allowedMethods.create).to.eq(true);
        expect(allowedMethods.update).to.eq(true);
        expect(allowedMethods.remove).to.eq(true);
    });
});