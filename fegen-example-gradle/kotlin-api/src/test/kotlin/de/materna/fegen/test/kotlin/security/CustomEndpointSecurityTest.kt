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