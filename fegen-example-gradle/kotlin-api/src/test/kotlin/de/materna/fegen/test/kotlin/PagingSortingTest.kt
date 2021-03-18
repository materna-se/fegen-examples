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

import de.materna.fegen.example.gradle.kotlin.api.Contact
import de.materna.fegen.runtime.PagedItems
import io.kotlintest.matchers.collections.shouldBeSortedWith
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.collections.shouldNotHaveSize
import io.kotlintest.shouldBe
import java.lang.Integer.min

class PagingSortingTest : ApiSpec() {

    val pageSize = 5

    init {
        "first page" {
            val page = 0
            val response = apiClient().contactRepository.readAll(page, pageSize)

            response.page.number shouldBe page
            response.page.size shouldBe pageSize
            response.items.size shouldBe pageSize
            response.items shouldBe apiClient().contactRepository.readAll().items.page(page)
        }

        "second page" {
            val page = 1
            val response = apiClient().contactRepository.readAll(page = page, size = pageSize)

            val expectedSize = min(pageSize, allContacts().page.totalElements - page * pageSize)
            response.page.number shouldBe page
            response.page.size shouldBe pageSize
            response.items.size shouldBe expectedSize
            response.items shouldBe allContacts().items.page(page)
        }

        "sorted" {
            val sorted = allContactsSorted()

            sorted.items shouldNotHaveSize 0
            sorted.items shouldHaveSize sorted.page.totalElements
            sorted.items shouldBeSortedWith compareBy { it.lastName }
        }

        "reverse sorted" {
            val sorted = allContactsSorted(reversed = true)

            sorted.items shouldNotHaveSize 0
            sorted.items shouldHaveSize sorted.page.totalElements
            sorted.items shouldBeSortedWith compareByDescending { it.firstName }
        }

        "sorted page" {
            val page = 1
            val response = apiClient().contactRepository.readAll(page, pageSize, "lastName,ASC")

            val expectedSize = min(pageSize, allContactsSorted().page.totalElements - page * pageSize)
            response.page.number shouldBe page
            response.page.size shouldBe pageSize
            response.items.size shouldBe expectedSize
            response.items shouldBe apiClient().contactRepository.readAll(sort = "lastName,ASC").items.page(page)
        }

        "reverse sorted page" {
            val page = 1
            val response = apiClient().contactRepository.readAll(page, pageSize, "firstName,DESC")

            val sorted = allContactsSorted(reversed = true)
            val expectedSize = min(pageSize, sorted.page.totalElements - page * pageSize)
            response.page.number shouldBe page
            response.page.size shouldBe pageSize
            response.items.size shouldBe expectedSize
            response.items shouldBe sorted.items.page(page)
        }
    }

    private fun allContacts() =
            apiClient().contactRepository.readAll()

    private fun allContactsSorted(reversed: Boolean = false): PagedItems<Contact> {
        val sort = if (reversed) "firstName,DESC" else  "lastName,ASC"
        return apiClient().contactRepository.readAll(sort = sort)
    }

    private fun List<Contact>.page(pageNumber: Int): List<Contact> {
        val start = pageNumber * pageSize
        val end = min((pageNumber + 1) * pageSize, this.size)
        return this.subList(start, end)
    }
}
