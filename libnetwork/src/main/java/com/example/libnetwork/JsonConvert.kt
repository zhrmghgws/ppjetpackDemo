package com.example.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

class JsonConvert<T>:Convert<T> {
    override fun convert(response: String, type: Type): T? {
        var jsonObject=JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if(data!=null){
            val data1 = data?.get("data")
            return JSON.parseObject(data1.toString(),type)
        }
        return null
    }

    override fun convert(response: String, claz: Class<*>): T? {
        var jsonObject=JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if(data!=null){
            val data1 = data?.get("data")
            return JSON.parseObject(data1.toString(),claz) as T
        }
        return null
    }
}