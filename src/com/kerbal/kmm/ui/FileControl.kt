package com.kerbal.kmm.ui

import com.kerbal.kmm.Settings
import com.kerbal.kmm.io.toPath
import javafx.event.EventTarget
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import tornadofx.*
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
class FileControl : GridPane() {
    var path: Path
        get() = textField.text.toPath()
        set(value) {
            textField.text = value.toAbsolutePath().toString()
        }

    var dialogTitle: String = "Choose File"
    var dialogFilters: Array<FileChooser.ExtensionFilter> = arrayOf(FileChooser.ExtensionFilter("All Files (*.*)", "*.*"))
    var dialogMode: FileChooserMode = FileChooserMode.Single

    private var textField: TextField by singleAssign()

    init {
        with(this) {
            hgap = Settings.UI_SPACING
            row {
                textField = textfield {
                    gridpaneConstraints {
                        hGrow = Priority.ALWAYS
                    }
                }
                button("Browse") {
                    setOnAction {
                        textField.text = chooseFile(dialogTitle, dialogFilters, dialogMode).first().absolutePath
                    }
                }
            }
        }
    }
}

fun EventTarget.fileControl(op: FileControl.() -> Unit) = opcr(this, FileControl(), op)