package com.example.libcommon

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.layout_empty_view.view.*

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
        orientation = VERTICAL
        gravity = Gravity.CENTER
    }

    fun setEmptyIcon(iconRes :Int){
        empty_icon.setImageResource(iconRes)
    }
    fun setTitle(title:String){
        when(title.isEmpty()){
            true ->{empty_text.visibility= View.GONE}
            false ->{
                empty_text.text=title
                empty_text.visibility=View.VISIBLE
            }
        }

    }
    fun setButton(title:String,listener:View.OnClickListener){
        when(title.isEmpty()){
            true ->{empty_action.visibility= View.GONE}
            false ->{
                empty_action.text=title
                empty_action.visibility=View.VISIBLE
                empty_action.setOnClickListener(listener)
            }
        }

    }
}