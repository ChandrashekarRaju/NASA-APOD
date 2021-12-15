package com.nasa.apod.utils

import timber.log.Timber
import kotlin.properties.Delegates

internal object Variables {
    var isNetworkConnected by Delegates.observable(false) { _, _, newValue ->
        Timber.i("Network Monitor: Network connectivity $newValue")
    }
}