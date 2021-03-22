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
package de.materna.fegen.test.kotlin.security

import de.materna.fegen.test.kotlin.ApiSpec
import de.materna.fegen.test.kotlin.apiClient
import de.materna.fegen.test.kotlin.login
import io.kotlintest.shouldBe

class CustomEndpointSecurityTest: ApiSpec() {

    init {
        "unsecured custom endpoint is allowed for anonymous" {
            val apiClient = apiClient()
            apiClient.testRestControllerClient.isReturnVoidAllowed() shouldBe true
        }

        "secured custom endpoint is not allowed for anonymous" {
            val apiClient = apiClient()
            apiClient.testRestControllerClient.isSecuredEndpointAllowed("user") shouldBe false
        }

        "secured custom endpoint with user parameter is allowed for reader" {
            val apiClient = login("reader", "pwd")
            apiClient.testRestControllerClient.isSecuredEndpointAllowed("user") shouldBe true
        }

        "secured custom endpoint with admin parameter is not allowed for reader" {
            val apiClient = login("reader", "pwd")
            apiClient.testRestControllerClient.isSecuredEndpointAllowed("admin") shouldBe false
        }

        "secured custom endpoint with admin parameter is allowed for admin" {
            val apiClient = login("admin", "pwd")
            apiClient.testRestControllerClient.isSecuredEndpointAllowed("admin") shouldBe true
        }
    }
}