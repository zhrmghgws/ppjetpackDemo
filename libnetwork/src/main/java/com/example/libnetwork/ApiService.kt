package com.example.libnetwork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiService {
    internal var okHttpClient: OkHttpClient?=null
    var sBaseUrl:String?=null
    init {
        val intercepter=HttpLoggingInterceptor()
        intercepter.level=HttpLoggingInterceptor.Level.BODY
        okHttpClient=OkHttpClient.Builder()
            .readTimeout(5,TimeUnit.SECONDS)
            .writeTimeout(5,TimeUnit.SECONDS)
            .connectTimeout(5,TimeUnit.SECONDS)
            .addInterceptor(intercepter)
            .build()
        val trustManager= arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

        })
        val ssl=SSLContext.getInstance("SSL")
        ssl.init(null,trustManager, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(ssl.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
    }
    fun initBase(baseUrl:String){
        sBaseUrl=baseUrl
    }
    fun <T>get(url:String):GetRequest<T>{
        return GetRequest(sBaseUrl+url)
    }
    fun <T>post(url:String):PostRequest<T>{
        return PostRequest(sBaseUrl+url)
    }
}