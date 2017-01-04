package com.kerbal.kmm

import com.kerbal.kmm.io.*
import com.kerbal.kmm.ui.MainApp
import javafx.application.Application
import javafx.collections.ObservableList
import tornadofx.observable
import java.io.File
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
object KerbalModManager {
    var state: KmmState
        get() = KmmState(
                archiveNames = archives.map { FileLocations.baseFolder.relativize(it.path.toAbsolutePath()).toString() }
        )
        set(state) {
            state.archiveNames
                    .map(String::toPath)
                    .map { FileLocations.baseFolder.resolve(it).toAbsolutePath() }
                    .filter(Path::exists)
                    .map(Path::toAbsolutePath)
                    .map { path -> Archive.create(path) }
                    .forEach { archive -> archives.add(archive) }
        }

    val archives: ObservableList<Archive> = mutableListOf<Archive>().observable()


    fun readState() {
        if (FileLocations.stateFile.exists()) {
            state = Resources.gson.fromJson(FileLocations.stateFile.readAllText(), KmmState::class.java)
        } else {
            state = KmmState()
        }
    }

    fun writeState() {
        FileLocations.stateFile.writeAllText(Resources.gson.toJson(state))
    }
}

data class KmmState(val archiveNames: List<String> = listOf())


fun main(args: Array<String>) {
    KerbalModManager.readState()
    Application.launch(MainApp::class.java, *args)
    KerbalModManager.writeState()
}