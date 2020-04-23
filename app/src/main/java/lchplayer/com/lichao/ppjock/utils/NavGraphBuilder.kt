package lchplayer.com.lichao.ppjock.utils

import android.content.ComponentName
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator

object NavGraphBuilder{
    public fun build(controller: NavController){
        val provider = controller.navigatorProvider
        var fragmentNavigator=provider.getNavigator(FragmentNavigator::class.java)
        var activityNavigator=provider.getNavigator(ActivityNavigator::class.java)
        var navGraph=NavGraph(NavGraphNavigator(provider))
        val destConfig=AppConfig.getDestConfig()
        for((key,value) in destConfig){
            if(value.isFragment){
                val destiantion = fragmentNavigator.createDestination()
                destiantion.className=value.claz
                destiantion.id=value.id
                destiantion.addDeepLink(value.pageUrl)
                navGraph.addDestination(destiantion)
            }else{
                val destination = activityNavigator.createDestination()
                destination.id=value.id
                destination.addDeepLink(value.pageUrl)
                destination.setComponentName(ComponentName(AppGlobals.getApplication().packageName,value.claz))
                navGraph.addDestination(destination)
            }
            if(value.asStarter){
                navGraph.startDestination=value.id
            }
        }
        controller.graph=navGraph
    }
}