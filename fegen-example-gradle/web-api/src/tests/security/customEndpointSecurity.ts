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

describe("Custom Endpoint Security", () => {

    beforeEach(setupTest)

    it("allows unsecured custom endpoint for anonymous", async () => {
        expect(await apiClient.testRestControllerClient.isReturnVoidAllowed()).to.eq(true);
    });

    it("forbids secured custom endpoint for anonymous", async () => {
        expect(await apiClient.testRestControllerClient.isSecuredEndpointAllowed("user")).to.eq(false);
    });

    it("allows secured custom endpoint with user parameter for reader", async () => {
        const loggedInClient = await loggedIn("reader", "pwd");
        expect(await loggedInClient.testRestControllerClient.isSecuredEndpointAllowed("user")).to.eq(true);
    });

    it("forbids secured custom endpoint with admin parameter for reader", async () => {
        const loggedInClient = await loggedIn("reader", "pwd");
        expect(await loggedInClient.testRestControllerClient.isSecuredEndpointAllowed("admin")).to.eq(false);
    });

    it("allows secured custom endpoint with admin parameter for admin", async () => {
        const loggedInClient = await loggedIn("admin", "pwd");
        expect(await loggedInClient.testRestControllerClient.isSecuredEndpointAllowed("admin")).to.eq(true);
    });
});