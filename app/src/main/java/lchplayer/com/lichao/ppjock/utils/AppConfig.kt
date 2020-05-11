package lchplayer.com.lichao.ppjock.utils

import com.example.libcommon.AppGlobals
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lchplayer.com.lichao.ppjock.model.BottomBar
import lchplayer.com.lichao.ppjock.model.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

object AppConfig {
    var sDestConfig: HashMap<String,Destination>?=null
    var sBottomBar:BottomBar?=null
    val gson=Gson()
    fun getDestConfig():HashMap<String,Destination>{
        if(sDestConfig==null){
            sDestConfig=HashMap()
            val parseFile = parseFile("navgraphjson.json")
            println("parseFile::::${parseFile}")
            sDestConfig= gson.fromJson(parseFile,object :
                TypeToken<HashMap<String, Destination>>(){}.type)
        }
        return sDestConfig!!
    }

    fun getBottomBar():BottomBar{
        if(sBottomBar==null){
            val content = parseFile("main_tabs_config.json")
            sBottomBar= gson.fromJson(content,BottomBar::class.java)

        }
        return sBottomBar!!
    }

    fun parseFile(fileName:String):String{
        val assets= AppGlobals.getApplication().resources.assets
        lateinit var stream:InputStream
        lateinit var reader:BufferedReader
        stream=assets.open(fileName)
        reader= BufferedReader(InputStreamReader(stream))
        val builder=StringBuilder()
        var line:String?
        while (reader.readLine().also { line=it }!=null){
            builder.append(line)
        }
        stream?.close()
        reader?.close()
        return builder.toString()
    }
}