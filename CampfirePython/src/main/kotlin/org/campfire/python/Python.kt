package org.campfire.python

import com.sup.dev.java.libs.json.Json

public class Python {
    companion object {
        var outputDir: String = ""
        var modelsPackageName: String = ""

        var pyClsPathPrefixLength: Int = 0
        
        fun initialize() {
            pyClsPathPrefixLength = modelsPackageName.length+1
        }

        fun procModel(cls: Class<*>) {
            val clsName = cls.getName()
            val clsSimpleName = clsName.split(".").last()
            val pyClsNameOrig = if(clsSimpleName[0] == 'M' && clsSimpleName[1].isUpperCase()) clsSimpleName.slice(1..clsSimpleName.length-1) else clsSimpleName
            val pyClsPath = clsName.slice(pyClsPathPrefixLength..clsName.length-clsSimpleName.length-1).replace(".", "/")

            val pyClsInner = pyClsNameOrig.contains("$")
            val pyFilePath = outputDir + "/models/" + pyClsPath + pyClsNameOrig.split("$")[0] + ".py"
            val pyClsName = if(pyClsInner) pyClsNameOrig.split("$")[1] else pyClsNameOrig
            
            var pyContent = "# This file was generated using scripts, your changes in it won't be in the next update.\n# Class " + clsName + "\n\n"

            val clsInst = cls::class.java.getDeclaredConstructor().newInstance()
            val clsJson = clsInst.json(false, Json()).getJson()
            println(clsJson)
        }
    }
}