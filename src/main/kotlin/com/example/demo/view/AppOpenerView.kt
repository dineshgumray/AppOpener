package com.example.demo.view

// package import space..
import javafx.scene.control.TextArea
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

// View space..
class AppOpenerView : View() {

    override val root = borderpane()

    // to accommodate needed .exe files
    private val ef = arrayOf(FileChooser.ExtensionFilter("Executables",
                                                        "*.exe"))
    // pathSaver.txt is used to store the path where .exe are present.
    private var file = File("pathSaver.txt")

    private lateinit var tfFA: TextArea

    init {
        println("pathSaver is created/exist ${file.createNewFile()}")

        with(root) {

            title = "Apps Opener"
            center =
            form {
                vbox{

                    spacing = 10.0
                    tfFA = textarea()
                    tfFA.text = (file.readText()
                                ).replace(";",
                                        "\n")

                    hbox {
                        spacing = 350.0
                        // Add Apps button is used to navigate to the.exe
                        // path and add it to pathSaver.txt
                        button("Add Apps") {
                            action {
                                val fn: List<File> = chooseFile(
                                        "Choose App", ef,
                                        FileChooserMode.Single,
                                        root.scene.window)

                                if (fn.isNotEmpty()) {
                                    file.appendText("${fn.first()};")
                                    tfFA.text = (file.readText()
                                                ).replace(";",
                                                        "\n")
                                }
                            }
                        }

                        // Run Apps buttons is used to run all the selected apps
                        // all one one click (this is an automation task)
                        button("Run Apps") {
                            action {
                                try{
                                    val lines:List<String> = (file.readText()
                                                             ).split(";")
                                    for(line in lines) if(line!="") Runtime.getRuntime().exec(line)

                                } catch(e : Exception) { println("Error: $e") }
                            }
                        }
                    }
                }
            }
        }
    }
}

