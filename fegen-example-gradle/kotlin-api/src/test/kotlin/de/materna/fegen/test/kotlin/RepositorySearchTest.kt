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
package de.materna.fegen.test.kotlin

import io.kotlintest.matchers.collections.shouldNotHaveSize
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldNotBe

class RepositorySearchTest: ApiSpec() {

    init {
        "repo search" {
            val searchStr = "n"
            val results = apiClient().contactClient.searchFindByNameContaining(searchStr).items

            results shouldNotHaveSize 0
            results.forEach { "${it.firstName} ${it.lastName}" shouldContain searchStr }
        }

        "repo search with projection" {
            val searchStr = "n"
            val results = apiClient().contactClient.searchFindByNameContainingContactFull(searchStr).items

            val result = results.find { it.firstName == "With number" && it.lastName == "and address" }
            result shouldNotBe null
            result!!.address shouldNotBe null
        }
    }
}
