package lchplayer.com.lichao.ppjock.model

import android.os.Parcelable
import com.example.libcommon.PoKo
import kotlinx.android.parcel.Parcelize

data class Destination(val isFragment:Boolean=false,
                       val asStarter:Boolean=false,
                       val needLogin :Boolean=false,
                       val pageUrl:String ="",
                       val clzName:String="",
                       val id :Int=0)