package com.kerbal.kmm.io

import java.nio.file.Path

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
abstract class Archive(val path: Path) {

    abstract fun extractTo(destination: Path)

    companion object {
        val EXTENSION_ZIP = ".zip".toUpperCase()
        val EXTENSION_7Z: String = ".7z".toUpperCase()
        val supportedExtensions: List<String> = listOf(EXTENSION_ZIP)

        fun isSupported(file: Path): Boolean {
            val upr = file.toString().toUpperCase()
            return supportedExtensions.any { ext ->
                upr.endsWith(ext)
            }
        }

        fun create(file: Path): Archive {
            if (file.toString().toUpperCase().endsWith(EXTENSION_ZIP)) {
                return ZipArchive(file)
            } else {
                throw UnsupportedOperationException("Only zip archives are supported at this time")
            }
        }
    }

}