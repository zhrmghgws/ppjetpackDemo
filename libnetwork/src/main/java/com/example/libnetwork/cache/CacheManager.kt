package com.example.libnetwork.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {
    fun <T>sava(key:String,body:T ){
        var cache=Cache(key, toByteArray(body))
        CacheDatabase.getInstance().cacheDao().save(cache)
    }
    fun getCache(key:String):Any?{
        val cache = CacheDatabase.getInstance().cacheDao().getCache(key)
        if(cache!=null && cache.data!=null){
            return toObject(cache.data)
        }
        return null;
    }

    fun toObject(data:ByteArray):Any?{
        var bais:ByteArrayInputStream?=null
        var ois:ObjectInputStream?=null
        try {
            bais= ByteArrayInputStream(data)
            ois= ObjectInputStream(bais)
            return ois.readObject()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }finally {
            bais?.close()
            ois?.close()
        }
        return null
    }

    fun <T>toByteArray(body:T):ByteArray{
        var baos:ByteArrayOutputStream?=null
        var oos :ObjectOutputStream?=null

       try {
           baos= ByteArrayOutputStream()
           oos= ObjectOutputStream(baos)
           oos.writeObject(body)
           oos.flush()
           return baos.toByteArray()
       }catch (e:Exception){

       }finally {
           baos?.close()
           oos?.close()
       }
        return ByteArray(0)
    }
}