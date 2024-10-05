package com.alican.movieskmp

import androidx.compose.ui.window.ComposeUIViewController
import di.initKoin
import ui.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}