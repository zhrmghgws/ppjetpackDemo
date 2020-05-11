package com.example.libnetwork

import java.lang.StringBuilder
import java.net.URLEncoder

object UrlCreator {
    fun creatUrlFromParams(url:String,params:Map<String,Any>):String{
        var builder= StringBuilder()
        builder.append(url)
        if(url.indexOf("?")>0 || url.indexOf("&") >0){
            builder.append("&")
        }else {
            builder.append("?")
        }
        for((key,value) in params){
            var encodeValue=URLEncoder.encode(value.toString(),"UTF-8")
            builder.append(key).append("=").append(encodeValue).append("&")

        }
        builder.deleteCharAt(builder.length-1)
        return builder.toString()
    }
}