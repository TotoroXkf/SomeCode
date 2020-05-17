package com.xkf.ppjoke.base

import android.content.ComponentName
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator

object NavGraphBuilder {
    fun build(navController: NavController) {
        val provider = navController.navigatorProvider
        val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
        val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
        val navGraph = NavGraph(NavGraphNavigator(provider))
        val destConfig = AppConfig.destinationMap
        for (destination in destConfig.values) {
            if (destination.isFragment) {
                val fragmentDestination = fragmentNavigator.createDestination()
                fragmentDestination.id = destination.id
                fragmentDestination.className = destination.className
                fragmentDestination.addDeepLink(destination.pageUrl)
                navGraph.addDestination(fragmentDestination)
            } else {
                val activityDestination = activityNavigator.createDestination();
                activityDestination.id = destination.id
                activityDestination.setComponentName(
                    ComponentName(
                        AppGlobal.getApplication().packageName,
                        destination.className
                    )
                )
                activityDestination.addDeepLink(destination.pageUrl)
                navGraph.addDestination(activityDestination)
            }
            
            if (destination.asStarter) {
                navGraph.startDestination = destination.id
            }
        }
        navController.graph = navGraph
    }
}