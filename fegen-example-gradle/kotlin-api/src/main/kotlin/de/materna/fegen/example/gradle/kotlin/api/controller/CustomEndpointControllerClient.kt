package de.materna.fegen.example.gradle.kotlin.api.controller

import de.materna.fegen.example.gradle.kotlin.api.Contact
import de.materna.fegen.example.gradle.kotlin.api.ContactDto
import de.materna.fegen.example.gradle.kotlin.api.CreateRequest
import de.materna.fegen.runtime.RequestAdapter
import de.materna.fegen.runtime.appendParams
import de.materna.fegen.runtime.isEndpointCallAllowed
import kotlin.Boolean
import kotlin.String
import kotlin.Suppress

class CustomEndpointControllerClient(
    private val requestAdapter: RequestAdapter
) {
    @Suppress("UNUSED")
    suspend fun createContact(body: CreateRequest): Contact {
        val url = """/api/custom/contacts/create""".appendParams()
        return requestAdapter.doSingleRequest<Contact, ContactDto, CreateRequest>(
            url = url,
            method = "POST",
            body = body,
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isCreateContactAllowed(): Boolean {
        val url = """/api/custom/contacts/create"""
        return isEndpointCallAllowed(requestAdapter.request, "POST", url)
    }

    @Suppress("UNUSED")
    suspend fun createOrUpdateContact(
        userName: String,
        firstName: String,
        lastName: String,
        number: String,
        street: String,
        zip: String,
        city: String,
        country: String
    ): Contact {
        val url = """/api/custom/contacts/createOrUpdate""".appendParams(
            "userName" to userName,
            "firstName" to firstName,
            "lastName" to lastName,
            "number" to number,
            "street" to street,
            "zip" to zip,
            "city" to city,
            "country" to country
        )
        return requestAdapter.doSingleRequest<Contact, ContactDto>(
            url = url,
            method = "POST",
            ignoreBasePath = true
        )
    }

    @Suppress("UNUSED")
    suspend fun isCreateOrUpdateContactAllowed(): Boolean {
        val url = """/api/custom/contacts/createOrUpdate"""
        return isEndpointCallAllowed(requestAdapter.request, "POST", url)
    }
}
