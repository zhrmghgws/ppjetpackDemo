package lchplayer.com.lichao.ppjock.utils

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.example.libcommon.AppGlobals
import lchplayer.com.lichao.ppjock.FixFragmentNavigator

object NavGraphBuilder{
    public fun build(controller: NavController,activity:FragmentActivity,containerId:Int){
        val provider = controller.navigatorProvider
        var navGraph=NavGraph(NavGraphNavigator(provider))
        var fragmentNavigator=FixFragmentNavigator(activity,activity.supportFragmentManager,containerId)
        provider.addNavigator(fragmentNavigator)
        var activityNavigator=provider.getNavigator(ActivityNavigator::class.java)
        val destConfig=AppConfig.getDestConfig()
        for((key,value) in destConfig){
            if(value.isFragment){
                val destiantion = fragmentNavigator.createDestination()
                destiantion.className=value.clzName
                destiantion.id=value.id
                destiantion.addDeepLink(value.pageUrl)
                navGraph.addDestination(destiantion)
            }else{
                val destination = activityNavigator.createDestination()
                destination.id=value.id
                destination.addDeepLink(value.pageUrl)
                destination.setComponentName(ComponentName(AppGlobals.getApplication().packageName,value.clzName))
                navGraph.addDestination(destination)
            }
            if(value.asStarter){
                navGraph.startDestination=value.id
            }
        }
        controller.graph=navGraph
    }
}