package org.campfire.python

import org.reflections.Reflections
import org.reflections.scanners.Scanners.*
import com.sup.dev.java.libs.json.JsonParsable

public class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val outputDir = System.getProperty("user.dir") + "/campfire-py-output"
            val modelsPackageName = "com.dzen.campfire.api.models"
            Python.outputDir = outputDir
            Python.modelsPackageName = modelsPackageName
            Python.initialize()

            println("Output directory: " + outputDir)

            val initializing = Task("Initializing models")
            val modelsRef = Reflections(modelsPackageName);
            val modelsClasses = modelsRef.getSubTypesOf(JsonParsable::class.java);
            initializing.end()

            val modelsProc = Task("Processing models", modelsClasses.size)
            for(cls in modelsClasses) {
                modelsProc.add(cls.getSimpleName())
                if(!cls.getName().startsWith(modelsPackageName))
                    continue
                Python.procModel(cls)
            }
            modelsProc.end()
        }
    }
}