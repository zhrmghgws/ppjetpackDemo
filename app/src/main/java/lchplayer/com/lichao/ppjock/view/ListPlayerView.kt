package lchplayer.com.lichao.ppjock.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.libcommon.PixUtils
import kotlinx.android.synthetic.main.layout_player_view.view.*
import lchplayer.com.lichao.ppjock.R

class ListPlayerView(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0, defStyleRes: Int=0) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    var mCategory:String?=null
    var mVideoUrl:String?=null
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_player_view,this,true)

    }

    fun bindData(category:String,widthPx:Int,heightPx:Int,coverUrl:String,videoUrl:String){
        mCategory=category
        mVideoUrl=videoUrl
        PPImageView.setImageUrl(cover,coverUrl,false)
        if(widthPx<heightPx){
            blur_backgroud.setBlurImageUrl(coverUrl,10)
            blur_backgroud.visibility= View.VISIBLE
        }else{
            blur_backgroud.visibility=View.INVISIBLE
        }
        setSize(widthPx,heightPx)
    }

    private fun setSize(widthPx: Int, heightPx: Int) {
        var maxWidth=PixUtils.getScreenWidth()
        var maxHeight=maxWidth
        var layoutWidth=maxWidth
        var layoutHeight:Int
        var coverWidth:Int
        var coverHeight:Int
        if(widthPx>=heightPx){
            coverWidth=maxWidth
            layoutHeight = (heightPx/(widthPx*1.0f/maxWidth)).toInt()
            coverHeight = layoutHeight
        }else{
            layoutHeight=maxHeight
            coverHeight=maxHeight
            coverWidth= (widthPx/(heightPx*1.0f/maxHeight)).toInt()
        }
        layoutParams.width=layoutWidth
        layoutParams.height=layoutHeight

        blur_backgroud.layoutParams.width=layoutWidth
        blur_backgroud.layoutParams.height=layoutHeight

        var coverParams=cover.layoutParams as LayoutParams
        coverParams.width=coverWidth
        coverParams.height=coverHeight
        coverParams.gravity=Gravity.CENTER
        cover.layoutParams=coverParams

        var playParams=play_btn.layoutParams as FrameLayout.LayoutParams
        playParams.gravity=Gravity.CENTER
        play_btn.layoutParams=playParams
    }
}