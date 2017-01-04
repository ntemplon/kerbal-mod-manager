package com.kerbal.kmm.io

import com.kerbal.kmm.util.fromJson
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Copyright (c) 2016 Nathan Templon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
fun Path.exists(): Boolean = Files.exists(this)

fun Path.doesNotExist(): Boolean = !this.exists()
fun Path.isDirectory(): Boolean = Files.isDirectory(this)
fun String.toPath(): Path = Paths.get(this)

fun Path.mkdirs() = Files.createDirectories(this)
fun Path.readAllLines(): List<String> = Files.readAllLines(this)
fun Path.readAllText(): String = this.readAllLines().joinToString(separator = System.lineSeparator())
fun Path.writeAllText(text: String): Path = Files.write(this, listOf(text))
fun Path.writeAllLines(text: Iterable<String>): Path = Files.write(this, text)
fun Path.inputStream(): InputStream = Files.newInputStream(this)
fun Path.outputStream(): OutputStream = Files.newOutputStream(this)
fun Path.directoryStream(): DirectoryStream<Path> = Files.newDirectoryStream(this)
inline fun <reified T : Any> Path.readJson(): T = this.readAllLines().fromJson()