package lchplayer.com.lichao.ppjock.model

data class Destination(val isFragment:Boolean=false,
                       val asStarter:Boolean=false,
                       val needLogin :Boolean=false,
                       val pageUrl:String ="",
                       val claz:String="",
                       val id :Int=0)