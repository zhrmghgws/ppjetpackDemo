package lchplayer.com.lichao.ppjock.utils

object StringConvert {
    fun converFeedUgc(value:Int):String{
        return if(value<10000) value.toString() else "${value/10000}万"
    }
}