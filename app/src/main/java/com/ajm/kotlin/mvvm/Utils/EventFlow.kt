package com.ajm.kotlin.mvvm.Utils

import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> mutableEventFlow(): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1
    )
}
