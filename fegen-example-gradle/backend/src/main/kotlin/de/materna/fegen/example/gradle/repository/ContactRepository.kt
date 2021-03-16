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
package de.materna.fegen.example.gradle.repository

import de.materna.fegen.example.gradle.entity.Contact
import de.materna.fegen.util.spring.annotation.FegenIgnore
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RepositoryRestResource
interface ContactRepository : JpaRepository<Contact, Long> {

    @RequestMapping
    @Query("SELECT c FROM Contact c WHERE c.firstName LIKE CONCAT('%', :name, '%') OR c.lastName LIKE CONCAT('%', :name, '%')")
    fun findByNameContaining(
            @RequestParam("name") @Param("name") name: String,
            pageable: Pageable
    ): Page<Contact>

    @Query("SELECT c FROM Contact c")
    fun getAllBaseProjected(): List<Contact.BaseProjection>

    @Query("SELECT c FROM Contact c WHERE c.firstName = :firstName AND c.lastName = :lastName")
    fun findByNames(
            @Param("firstName") firstName: String,
            @Param("lastName") lastName: String
    ): Contact?

    @FegenIgnore
    fun findByFirstNameStartingWith(prefix: String): List<Contact.BaseProjection>
}
