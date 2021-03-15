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
package de.materna.fegen.test.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

open class SpringBackgroundStop: DefaultTask() {

    @get:Input
    var springProject: Project? = null

    @TaskAction
    fun stop() {
        val client = OkHttpClient()
        val requestBody = byteArrayOf().toRequestBody(null, 0, 0)
        val request = Request.Builder()
                .url("http://localhost:8080/api/exit")
                .method("POST", requestBody)
                .build()
        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    logger.info("Backend shut down successfully")
                    return
                } else {
                    throw GradleException("Backend did not shut down. Endpoint returned ${response.code}")
                }
            }
        } catch (e: java.net.ConnectException) {
            logger.info("Backend was not running")
        } catch (e: Exception) {
            if (e is GradleException) {
                throw e
            } else {
                throw GradleException("Backend could not reached", e)
            }
        }
    }

}