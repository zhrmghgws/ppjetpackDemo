package lchplayer.com.lichao.ppjock.model

import android.os.Parcelable
import com.example.libcommon.PoKo
import kotlinx.android.parcel.Parcelize

data class BottomBar(var activeColor:String,
                     var inActiveColor:String,
                     var tabs:List<Tabs>,
                     var selectTab:Int)
data class Tabs(var size:Int,
                var enable:Boolean,
                var index:Int,
                var pageUrl:String,
                var title:String,
                var tintColor:String)