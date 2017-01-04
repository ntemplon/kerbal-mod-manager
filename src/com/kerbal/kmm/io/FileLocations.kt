package com.kerbal.kmm.io

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.properties
import kotlin.reflect.memberProperties

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
object FileLocations {

    val baseFolder: Path = File(FileLocations::class.java.protectionDomain.codeSource.location.toURI()).toPath().toAbsolutePath()
    val archivesFolder: Path = baseFolder.resolve("archives")
    val modFolder: Path = baseFolder.resolve("mods")

    val preferencesFile: Path = baseFolder.resolve("prefs.cfg")
    val stateFile: Path = baseFolder.resolve("kmm.cfg")

    init {
        FileLocations::class.memberProperties.filter { prop ->
            prop.returnType.javaType == Path::class.java
        }.filter { prop ->
            prop.name.toUpperCase().endsWith("FOLDER")
        }.map { prop ->
            prop.get(this) as? Path
        }.filterNotNull().filter(Path::doesNotExist).forEach { path ->
            path.mkdirs()
        }
    }
}