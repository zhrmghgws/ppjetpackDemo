package lchplayer.com.lichao.ppjock.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.libcommon.PixUtils
import com.example.libcommon.PixUtils.dp2px

class PPImageView : AppCompatImageView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun bindData(
        widthPx: Int,
        heightPx: Int,
        marginLeft: Int,
        maxWidth: Int=PixUtils.getScreenWidth(),
        maxHeight: Int=PixUtils.getScreenHeight(),
        imageUrl: String?
    ) {
        if (widthPx <= 0 || heightPx <= 0) {
            Glide.with(this).load(imageUrl)
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        val height = resource.intrinsicHeight
                        val width = resource.intrinsicWidth
                        setSize(width, height, marginLeft, maxWidth, maxHeight)
                        setImageDrawable(resource)
                    }
                })
            return
        }
        setSize(widthPx, heightPx, marginLeft, maxWidth, maxHeight)
        setImageUrl(this, imageUrl, false)
    }

    private fun setSize(
        width: Int,
        height: Int,
        marginLeft: Int,
        maxWidth: Int=PixUtils.getScreenWidth(),
        maxHeight: Int=PixUtils.getScreenHeight()
    ) {
        val finalWidth: Int
        val finalHeight: Int
        if (width > height) {
            finalWidth = maxWidth
            finalHeight = (height / (width * 1.0f / finalWidth)).toInt()
        } else {
            finalHeight = maxHeight
            finalWidth = (width / (height * 1.0f / finalHeight)).toInt()
        }
        val params = MarginLayoutParams(finalHeight, finalHeight)
        params.leftMargin = if (height > width) dp2px(marginLeft) else 0
        layoutParams = params
    }

    fun setBlurImageUrl(coverUrl: String, radius: Int) {
        Glide.with(this).load(coverUrl).override(50)
            .transform()
            .dontAnimate()
            .into(object :SimpleTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    background=resource
                }

            })
    }

    companion object {
        @BindingAdapter(value = ["image_url", "isCircle"], requireAll = false)
        fun setImageUrl(
            view: PPImageView,
            imageUrl: String?,
            isCircle: Boolean
        ) {
            val builder =
                Glide.with(view).load(imageUrl)
            if (isCircle) {
                builder.transform(CircleCrop())
            }
            val layoutParams = view.layoutParams
            if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
                builder.override(layoutParams.width, layoutParams.height)
            }
            builder.into(view)
        }
    }
}