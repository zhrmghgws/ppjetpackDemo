package com.example.libcommon

import android.app.Application

object AppGlobals {
    var sApplication:Application?=null
    fun getApplication():Application{
        if(sApplication==null){
            val declaredMethod = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication")
            sApplication=declaredMethod.invoke(null) as Application
        }
        return sApplication!!
    }
}