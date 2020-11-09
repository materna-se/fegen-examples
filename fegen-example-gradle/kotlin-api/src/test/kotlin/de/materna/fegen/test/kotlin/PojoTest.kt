package de.materna.fegen.test.kotlin

import de.materna.fegen.example.gradle.kotlin.api.ComplexTestPojo
import de.materna.fegen.example.gradle.kotlin.api.CreateRequest
import de.materna.fegen.example.gradle.kotlin.api.PrimitiveTestPojo
import de.materna.fegen.example.gradle.kotlin.api.RecursiveTestPojo
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

class PojoTest : ApiSpec() {

    init {
        "custom endpoint with pojo as body" {
            val result = apiClient().customEndpointControllerClient.createContact(CreateRequest(
                    userName = "UserOne",
                    firstName = "firstName",
                    lastName = "lastName",
                    number = "",
                    street = "street",
                    zip = "12345",
                    city =  "city",
                    country = "country")
            )

            result shouldNotBe null
            val owner = apiClient().contactClient.readOwner(result)
            owner shouldNotBe null
            owner!!.name shouldBe "UserOne"
            result.firstName shouldBe "firstName"
            result.lastName shouldBe "lastName"
            result.number shouldBe null
            val address = apiClient().contactClient.readAddress(result)
            address shouldNotBe null
            address!!.street shouldBe "street"
            address.zip shouldBe "12345"
            address.city shouldBe "city"
            address.country shouldBe "country"
        }

        "pojo list as return value" {
            val result = apiClient().testRestControllerClient.pojosAsReturnValue()
            result shouldNotBe null
            result.size shouldBe 2
        }

        "pojo as body and single return value" {
            val result = apiClient().testRestControllerClient.pojoAsBodyAndReturnValue(ComplexTestPojo(listOf(PrimitiveTestPojo(true, 42.0, "test"))))
            result shouldNotBe null
            result.pojos.size shouldBe 1
        }

        "pojo as body and list return value" {
            val body = ComplexTestPojo(listOf(PrimitiveTestPojo(true, 42.0, "test")))
            val result = apiClient().testRestControllerClient.pojoAsBodyAndListReturnValue(body)
            result shouldNotBe null
            result.size shouldBe 2
        }

        "pojo list as body" {
            val body = listOf(PrimitiveTestPojo(true, 42.0, "test"))
            val result = apiClient().testRestControllerClient.pojoListAsBody(body)
            result shouldNotBe null
            result.size shouldBe 1
        }

        "recursive pojo as body and return type" {
            val body = RecursiveTestPojo(null)
            val result = apiClient().testRestControllerClient.recursivePojo(body)
            result shouldBe RecursiveTestPojo(RecursiveTestPojo(null))
        }
    }
}