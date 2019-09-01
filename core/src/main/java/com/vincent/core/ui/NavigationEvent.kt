package com.vincent.core.ui

import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes

abstract class NavigationEvent {

    data class UriEvent(val uri: Uri, val args: Bundle?) : NavigationEvent()

    data class IdEvent(@IdRes val id: Int, val args: Bundle?): NavigationEvent()
}