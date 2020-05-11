package com.example.libnetwork

import okhttp3.FormBody

class PostRequest<T>(mUrl:String) : Request<T, PostRequest<T>>(mUrl) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        var bodyBuilder=FormBody.Builder()
        for((key,value) in params){
            bodyBuilder.add(key,value.toString())
        }
        return builder.url(mUrl).post(bodyBuilder.build()).build()
    }
}