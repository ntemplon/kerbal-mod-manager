package com.kerbal.kmm.io

import java.io.FileInputStream
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

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
class ZipArchive(path: Path) : Archive(path) {
    override fun extractTo(destination: Path) {
        // Make sure that the target folder exists
        if (!destination.exists()) {
            destination.mkdirs()
        }

        this.path.inputStream().use { fis ->
            ZipInputStream(fis).use { zis ->
                zis.entries().forEach { entry ->
                    val target = destination.resolve(entry.name)

                    if (entry.isDirectory) {
                        if (target.doesNotExist()) {
                            target.mkdirs()
                        }
                    } else {
                        val folder = target.parent
                        if (folder.doesNotExist()) {
                            folder.mkdirs()
                        }

                        zis.extractCurrentItem(target)
                    }
                }
            }
        }
    }
}


fun ZipInputStream.extractCurrentItem(file: Path) {
    val buffer = ByteArray(2048)

    file.outputStream().use { out ->
        var count = this.read(buffer)
        while (count > 0) {
            out.write(buffer, 0, count)
            count = this.read(buffer)
        }
    }
}


fun ZipInputStream.entries(): ZipEntrySequence = ZipEntrySequence(this)

class ZipEntrySequence(val stream: ZipInputStream) : Sequence<ZipEntry> {
    override fun iterator(): Iterator<ZipEntry> = ZipEntryIterator(stream)
}

class ZipEntryIterator(val stream: ZipInputStream) : Iterator<ZipEntry> {
    private var nextEntry: ZipEntry? = null
    private var entryReturned: Boolean = true
    private var lastHasNext: Boolean = false

    override fun hasNext(): Boolean {
        if (entryReturned) {
            if (nextEntry != null) {
                stream.closeEntry()
            }

            nextEntry = stream.nextEntry
            entryReturned = false

            lastHasNext = (nextEntry != null)
        }

        return lastHasNext
    }

    override fun next(): ZipEntry {
        entryReturned = true
        return nextEntry!!
    }
}