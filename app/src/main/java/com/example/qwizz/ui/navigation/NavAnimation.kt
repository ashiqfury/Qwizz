package com.example.qwizz.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
object NavAnimation {
    private const val ANIMATION_DURATION = 300

    val enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?) = {
        slideIntoContainer(
            AnimatedContentScope.SlideDirection.Left,
            animationSpec = tween(ANIMATION_DURATION)
        )
    }
    val exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?) = {
        slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Left,
            animationSpec = tween(ANIMATION_DURATION)
        )
    }
    val popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?) = {
        slideIntoContainer(
            AnimatedContentScope.SlideDirection.Right,
            animationSpec = tween(ANIMATION_DURATION)
        )
    }
    val popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?) = {
        slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Right,
            animationSpec = tween(ANIMATION_DURATION)
        )
    }
}