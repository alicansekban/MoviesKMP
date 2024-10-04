package com.alican.movieskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform