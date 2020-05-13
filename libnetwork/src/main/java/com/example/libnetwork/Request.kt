package com.example.libnetwork

import android.annotation.SuppressLint
import android.os.Parcelable
import android.util.Log
import androidx.annotation.IntDef
import androidx.arch.core.executor.ArchTaskExecutor
import com.alibaba.fastjson.JSON
import com.example.libnetwork.cache.CacheManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

abstract class Request<T, R : Request<T, R>?>(val mUrl: String) :Cloneable{
    private var mClaz: Class<*>?=null
    private var mType: Type?=null
    protected var headers =
        HashMap<String, String>()
    protected var params =
        HashMap<String, Any>()
    private var cacheKey: String? = null
    private var mCacheStrategy= NET_ONLY

    @IntDef(
        CACHE_ONLY,
        CACHE_FIRST,
        NET_ONLY,
        NET_CACHE
    )
    annotation class CacheStrategy

    fun addHeader(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParam(key: String, value: Any): R {
        try {
            if(value.javaClass == String::class.java){
                params[key]=value
            }else{
                val type = value.javaClass.getField("TYPE")
                val clazz = type[null] as Class<*>
                if (clazz.isPrimitive) {
                    params[key] = value
                }
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return this as R
    }

    fun cacheKey(key: String?): R {
        cacheKey = key
        return this as R
    }

    fun cacheStrategy(@CacheStrategy cacheStrategy: Int):R{
        mCacheStrategy=cacheStrategy
        return this as R
    }

    fun responseType(type: Type):R{
        mType=type
        return this as R
    }
    fun responseType(claz: Class<*>):R{
        mClaz=claz
        return this as R
    }

    @SuppressLint("RestrictedApi")
    fun execute(callback: JsonCallback<T>) {
        if(mCacheStrategy != NET_ONLY){
            ArchTaskExecutor.getIOThreadExecutor().execute(object :Runnable{
                override fun run() {
                    var response:ApiResponse<T> = readCache()
                    callback?.run { callback.onCacheSuccess(response) }
                }

            })
        }
        if(mCacheStrategy!= CACHE_ONLY){
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    var response=ApiResponse<T>()
                    response.message=e.message
                    callback?.onError(response)
                }

                override fun onResponse(call: Call, response: Response) {
                    var apiResponse=parseResponse(response,callback)
                    if(apiResponse.success){
                        callback?.onSuccess(apiResponse)
                    }else{
                        callback?.onError(apiResponse)
                    }
                }
            })
        }

    }
    fun readCache():ApiResponse<T>{
        var key=if(cacheKey.isNullOrEmpty()) generateCacheKey() else cacheKey
        var cache=CacheManager.getCache(key!!)
        var result=ApiResponse<T>()
        result.status=304
        result.message="读取缓存成功"
        result.body=cache as T
        result.success=true
        return result

    }
    fun execute() :ApiResponse<T>{
        if(mCacheStrategy== CACHE_ONLY){
            return readCache()
        }
        var result:ApiResponse<T>?=null
        try {
            val response = call.execute()
            result= parseResponse(response, null)
        }catch (e:Exception){
            e.printStackTrace()
            if(result==null){
                result = ApiResponse()
                result.message=e.message
            }
        }
        return result!!
    }
    private val call: Call
        private get() {
            val builder = okhttp3.Request.Builder()
            addHeaders(builder)
            val request = generateRequest(builder)
            return ApiService.okHttpClient!!.newCall(request)
        }

    protected abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request

    private fun addHeaders(builder: okhttp3.Request.Builder) {
        for ((key, value) in headers) {
            builder.addHeader(key, value)
        }
    }
    private fun parseResponse(response:Response,callback :JsonCallback<T>?):ApiResponse<T>{
        var message:String?=""
        var status=response.code
        var success=response.isSuccessful
        var result=ApiResponse<T>()
        var convert=JsonConvert<T>()
        try {
            if(response.body!=null){
                var content=response.body!!.string()
                if(success){
                    if(callback!=null){
                        val type:ParameterizedType = callback.javaClass.genericSuperclass as ParameterizedType
                        var argument=type.actualTypeArguments[0]
                        result.body=convert?.convert(content,argument)
                    }else if(mType!=null){
                        result.body=convert?.convert(content,mType!!)
                    }else if(mClaz!=null){
                        result.body=convert?.convert(content,mClaz!!)

                    }else{
                        Log.e("respose:::","无法解析")
                    }
                }else{
                    message=content
                }
            }
        }catch (e:Exception){
            message=e.message
            success=false
            e.printStackTrace()
        }
        result.success=success
        result.status=status
        result.message=message

        if(mCacheStrategy!= NET_ONLY && result.success && result.body!=null && result.body is Parcelable){
            saveCache(result.body as T)
        }
        return result
    }
    fun saveCache(body:T){
        var key=if(cacheKey.isNullOrEmpty()) generateCacheKey() else cacheKey
        CacheManager.sava(key!!,body)
    }
    fun generateCacheKey():String{
        cacheKey=UrlCreator.creatUrlFromParams(mUrl,params)
        return cacheKey !!
    }

    public override fun clone(): Request<T,R>{
        return super.clone() as (Request<T,R>)
    }

    companion object {
        const val CACHE_ONLY = 1
        const val CACHE_FIRST = 2
        const val NET_ONLY = 3
        const val NET_CACHE = 4
    }

}