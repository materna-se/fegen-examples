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
package io.kotlintest.provided

import de.materna.fegen.test.kotlin.setupTest
import io.kotlintest.AbstractProjectConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.IllegalStateException

val MAX_TRIES = 30
val SLEEP = 1000L
val SERVER_ADDRESS = "http://localhost:8080"

object ProjectConfig: AbstractProjectConfig() {

    override fun beforeAll() {
        waitForServerReady()
    }

    private fun waitForServerReady() {
        print("Waiting for server to be available")
        System.out.flush()
        for (t in 0 until MAX_TRIES) {
            try {
                setupTest()
                println()
                println("Server reachable")
                return
            } catch (e: IOException) {
                // Connection could not yet be established
            }
            Thread.sleep(SLEEP)
            print(".")
            System.out.flush()
        }
        throw IllegalStateException("Server could not be reached after ${MAX_TRIES} tries")
    }
}