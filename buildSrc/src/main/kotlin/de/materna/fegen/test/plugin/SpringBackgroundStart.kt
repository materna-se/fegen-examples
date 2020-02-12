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
import org.gradle.api.plugins.BasePluginConvention
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

open class SpringBackgroundStart: DefaultTask() {

    @get:Input
    var springProject: Project? = null

    @TaskAction
    fun start() {
        val springProject = this.springProject
        springProject ?: throw GradleException("springProject must be configured")

        val process = startSpringProcess(springProject)

        val state = AtomicReference(SpringLogState.Starting)
        val taskFinishSemaphore = Semaphore(0)
        thread { errorReaderThread(process) }
        thread { outputReaderThread(process, state, taskFinishSemaphore) }
        taskFinishSemaphore.acquire()
        if (state.get().taskFailed()) {
            Thread.sleep(1000)
            throw GradleException("Starting backend failed")
        } else {
            logger.info("Backend started")
        }
    }

    private fun startSpringProcess(springProject: Project): Process {
        val libsDirName = springProject.convention.getPlugin(BasePluginConvention::class.java).libsDirName
        val libsDir = springProject.buildDir.resolve(libsDirName)
        val jarFile = libsDir.listFiles().singleOrNull() ?: throw GradleException("Multiple or no jars found in ${libsDir}. There should be exactly one jar containing a built spring application")
        logger.info("Starting spring server $jarFile")
        val javaBin = System.getProperty("java.home") + "/bin/java"
        return ProcessBuilder(javaBin, "-jar", jarFile.toString())
                .inheritIO()
                .start()

    }

    private fun outputReaderThread(process: Process, state: AtomicReference<SpringLogState>, taskFinishSemaphore: Semaphore) {
        var semaphore: Semaphore? = taskFinishSemaphore
        process.inputStream.reader().forEachLine { line ->
            state.set(state.get().nextLine(line))
            if (state.get().taskFinished()) {
                semaphore?.release()
                semaphore = null
            }
            if (state.get().shouldLog()) {
                System.err.println("  [ Backend ] $line")
            }
        }
    }

    private fun errorReaderThread(process: Process) {
        process.errorStream.reader().forEachLine { line ->
            System.err.println("  [ Backend ] $line")
        }
    }
}