package com.example.libcommon

object PixUtils {
    fun dp2px(dpValut:Int): Int {
        val metrics=AppGlobals.getApplication().resources.displayMetrics
        return (metrics.density*dpValut+0.5f).toInt()
    }

    fun getScreenWidth():Int{
        val metrics=AppGlobals.getApplication().resources.displayMetrics
        return metrics.widthPixels
    }

    fun getScreenHeight():Int{
        val metrics=AppGlobals.getApplication().resources.displayMetrics
        return metrics.heightPixels
    }
}