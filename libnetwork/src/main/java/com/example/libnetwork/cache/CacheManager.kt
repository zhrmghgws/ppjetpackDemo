package com.example.libnetwork.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {
    fun <T>sava(key:String,body:T ){
        var cache=Cache(key, toByteArray(body))
        CacheDatabase.getInstance().cacheDao().save(cache)
        println("CacheManager::::保存成功")
    }
    fun getCache(key:String):Any?{
        val cache = CacheDatabase.getInstance().cacheDao().getCache(key)
        if(cache!=null && cache.data!=null){
            return toObject(cache.data)
        }
        return null;
    }

    fun toObject(data:ByteArray):Any?{
        ObjectInputStream(ByteArrayInputStream(data)).use {
            return it.readObject()
        }
    }

    fun <T>toByteArray(body:T):ByteArray{
        val byte=ByteArrayOutputStream()
        ObjectOutputStream(byte).use {
            it.writeObject(body)
            it.flush()
            return byte.toByteArray()
        }
    }
}