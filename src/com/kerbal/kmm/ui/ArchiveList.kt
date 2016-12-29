package com.kerbal.kmm.ui

import com.kerbal.kmm.Settings
import com.kerbal.kmm.io.*
import com.kerbal.kmm.util.PlatformUtils
import javafx.event.EventTarget
import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.util.Callback
import tornadofx.*
import tornadofx.Stylesheet.Companion.listView
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
class ArchiveList : Fragment() {
    val archives = mutableListOf<Archive>().observable()

    var nameLabel: Label by singleAssign()
    var name = "Archives"
        get() = field
        set(value) {
            field = value
            nameLabel.text = value
        }

    override val root = borderpane {
        top {
            nameLabel = label {
                text = name
            }
        }

        center {
            listview(archives) {
                cellCache { archive ->
                    borderpane {
                        top {
                            label {
                                text = archive.path.fileName.toString()
                            }
                        }

                        center {
                            label {
                                text = "Not installed"
                                textFill = Color.RED
                            }
                        }

                        tooltip {
                            text = archive.path.toAbsolutePath().toString()
                        }

                        contextMenu = ContextMenu().apply {
                            menuitem("Open File Location") {
                                val selected = selectedItem
                                if (selected != null) {
                                    PlatformUtils.open(selected.path.parent)
                                }
                            }

                            menuitem("Open File") {
                                val selected = selectedItem
                                if (selected != null) {
                                    PlatformUtils.open(selected.path)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}