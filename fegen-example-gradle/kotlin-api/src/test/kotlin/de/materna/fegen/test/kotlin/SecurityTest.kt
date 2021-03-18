package de.materna.fegen.test.kotlin

import de.materna.fegen.runtime.EntitySecurity
import io.kotlintest.shouldBe

class SecurityTest: ApiSpec() {

    init {
        "all permissions for unsecured entity if anonymous" {
            val apiClient = apiClient()
            val allowedMethods = apiClient.primitiveTestEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = true,
                update = true,
                delete = true
            )
        }

        "no permissions for secured entity if anonymous" {
            val apiClient = apiClient()
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = false,
                readAll = false,
                create = false,
                update = false,
                delete = false
            )
        }

        "read permissions for secured entity if reader" {
            val apiClient = login("reader", "pwd")
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = false,
                update = false,
                delete = false
            )
        }

        "create permissions for secured entity if writer" {
            val apiClient = login("writer", "pwd")
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = true,
                update = false,
                delete = false
            )
        }

        "all permissions for secured entity if admin" {
            val apiClient = login("admin", "pwd")
            val allowedMethods = apiClient.securedEntityClient.allowedMethods()
            allowedMethods shouldBe EntitySecurity(
                readOne = true,
                readAll = true,
                create = true,
                update = true,
                delete = true
            )
        }
    }
}