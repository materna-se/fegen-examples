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
import fetch from "node-fetch";
import * as fetchCookie from "fetch-cookie";
import {ApiClient} from "../ApiClient";

export const BASE_URL = "http://localhost:8080/"

export const apiClient = new ApiClient(undefined, BASE_URL);

export function setupFetch() {
    // Use fetchCookie on order for authorization to work.
    // A new instance is created each time, so sessions are not retained between tests
    // @ts-ignore Types do not match exactly, but good enough for FeGen
    window.fetch = fetchCookie(fetch);
}

export async function setupTest() {
    await fetch("http://localhost:8080/api/setupTest", { method: "POST" });
}