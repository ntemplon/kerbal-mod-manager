package com.kerbal.kmm.ui

import com.kerbal.kmm.Settings
import com.kerbal.kmm.io.Archive
import com.kerbal.kmm.io.ZipArchive
import com.kerbal.kmm.io.directoryStream
import com.kerbal.kmm.io.toPath
import com.kerbal.kmm.util.PlatformUtils
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.stage.Stage
import tornadofx.*
import java.awt.Desktop
import java.io.File
import kotlin.concurrent.thread

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
class MainView : View() {
    var sourceControl: FileControl by singleAssign()
    var targetControl: DirectoryControl by singleAssign()
    val archiveList = ArchiveList()

    override val root = borderpane {
        padding = Insets(Settings.UI_SPACING)

        prefWidth = 500.0

        top {
            menubar {
                menu("File") {
                    menuitem("Exit") {
                        primaryStage.requestClose()
                    }
                }

                menu("Edit") {
                    menuitem("Preferences") {

                    }
                }
            }
        }

        this.center = archiveList.apply {
            val folder = System.getenv("HOME").toPath().resolve("Downloads")

            name = "Archives in \"$folder\""

            folder.directoryStream()
                    .filter { Archive.isSupported(it) }
                    .map { Archive.create(it) }
                    .forEach { archives.add(it) }
        }.root


        bottom {
            hbox {
                alignment = Pos.CENTER_RIGHT
                spacing = Settings.UI_SPACING

                button {
                    text = "Exit"
                    setOnAction { primaryStage.requestClose() }
                }
            }
        }
    }

    companion object {
        fun instance(): MainView = find(MainView::class)
    }
}

class MainApp : App() {
    override val primaryView = MainView::class

    override fun start(stage: Stage) {
        stage.setOnCloseRequest { e ->

        }

        super.start(stage)
    }
}