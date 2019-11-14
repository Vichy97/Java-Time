package com.vincent.core.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes

sealed class NavigationEvent {

    data class IdEvent(@IdRes val actionId: Int, val arguments: Bundle? = null) : NavigationEvent()
    data class UriEvent(val uri: Uri) : NavigationEvent()
    data class IntentEvent(val intent: Intent): NavigationEvent()
}