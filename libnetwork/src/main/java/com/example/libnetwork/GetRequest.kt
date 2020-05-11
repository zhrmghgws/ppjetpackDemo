package com.example.libnetwork

class GetRequest<T>(mUrl: String) : Request<T, GetRequest<T>>(mUrl) {
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        return builder.get().url(UrlCreator.creatUrlFromParams(mUrl, params)).build()
    }

}