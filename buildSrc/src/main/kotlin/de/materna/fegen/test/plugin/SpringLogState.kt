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

/**
 * Estimates the state of a spring application by parsing its standard output
 */
enum class SpringLogState {
    /**
     * The application is starting and not yet ready
     */
    Starting,
    /**
     * The application is running, ready to process requests and
     * not printing an exception to its standard output
     */
    Running,
    /**
     * The application is running and has printed an exception to its output.
     * The corresponding stack trace has not yet been printed
     */
    ExceptionSeen,
    /**
     The application is running and the last printed line belongs to a stack trace
     */
    StacktraceSeen,
    /**
     * The application has failed to start and will not be able to process requests
     */
    StartFailed;

    private val errorMessages = hashSetOf(
            "APPLICATION FAILED TO START",
            "Error starting ApplicationContext"
    )

    fun nextLine(line: String): SpringLogState {
        when (this) {
            Starting -> if (errorMessages.any { line.contains(it) }) {
                return StartFailed
            } else if (line.contains(Regex("Started .+ in .+ seconds"))) {
                return Running
            }
            Running -> if (line.contains("Exception: ")) {
                return ExceptionSeen
            }
            ExceptionSeen -> if (isStacktraceLine(line)) {
                return StacktraceSeen
            }
            StacktraceSeen -> return if (isStacktraceLine(line)) {
                StacktraceSeen
            } else {
                Running
            }
            else -> Unit
        }
        return this
    }

    /**
     * Should the last received line be printed to the gradle output
     */
    fun shouldLog() = when (this) {
        Starting -> false
        Running -> false
        else -> true
    }

    fun taskFinished() = this != Starting

    fun taskFailed() = this == StartFailed

    private fun isStacktraceLine(line: String) =
            line.contains("\tat ") ||
                    line.contains("Caused by: ") ||
                    line.matches(Regex("\t\\.\\.\\. \\d+ more"))
}