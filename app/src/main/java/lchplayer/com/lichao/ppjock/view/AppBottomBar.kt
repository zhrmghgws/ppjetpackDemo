package lchplayer.com.lichao.ppjock.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import lchplayer.com.lichao.ppjock.R
import lchplayer.com.lichao.ppjock.utils.AppConfig

class AppBottomBar @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    val sIcons= arrayOf(R.drawable.icon_tab_home,R.drawable.icon_tab_sofa,R.drawable.icon_tab_publish,R.drawable.icon_tab_find,R.drawable.icon_tab_mine)

    init {
        initView()
    }

    @SuppressLint("RestrictedApi")
    fun initView(){
        var bottomBar =AppConfig.getBottomBar()
        val tabs = bottomBar.tabs
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()
        var colors= intArrayOf(Color.parseColor(bottomBar.activeColor),Color.parseColor(bottomBar.inActiveColor))
        var colorStateList=ColorStateList(states,colors)
        itemIconTintList=colorStateList
        itemTextColor=colorStateList
        labelVisibilityMode=LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        //selectedItemId=bottomBar.selectTab
        println("tabs.size::${tabs.size}")
        for(tab in tabs!!){
            if(!tab.enable){
                continue
            }
            val id = getId(tab.pageUrl)
            if(id<0){
                continue
            }
            println("tab.index:::::${tab.index}")
            val item = menu.add(0, id, tab.index, tab.title)
            item.setIcon(sIcons[tab.index])
        }
        var index=0
        for(tab in tabs!!){
            if(!tab.enable){
                continue
            }
            var itemId=getId(tab.pageUrl)
            if(itemId<0){
                continue
            }

            val iconSize = dp2px(tab.size)
            val menuView:BottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
            val itemView:BottomNavigationItemView = menuView.getChildAt(index) as BottomNavigationItemView
            itemView.setIconSize(iconSize)

            if(TextUtils.isEmpty(tab.title)){
                val tintColor=if(tab.tintColor.isNullOrEmpty()) Color.parseColor("#ff678f") else Color.parseColor(tab.tintColor)
                itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                itemView.setShifting(false)

                /**
                 * 如果想要禁止掉所有按钮的点击浮动效果。
                 * 那么还需要给选中和未选中的按钮配置一样大小的字号。
                 *
                 *  在MainActivity布局的AppBottomBar标签增加如下配置，
                 *  @style/active，@style/inActive 在style.xml中
                 *  app:itemTextAppearanceActive="@style/active"
                 *  app:itemTextAppearanceInactive="@style/inActive"
                 */
            }
            index++
        }
    }

    fun dp2px(size:Int):Int{
        val value = context.resources.displayMetrics.density * size + 0.5f
        return value.toInt()
    }
    
    fun getId(pageUrl:String):Int{
        val destination = AppConfig.getDestConfig().get(pageUrl)
        return if(destination==null){ -1 }else destination.id
    }
}