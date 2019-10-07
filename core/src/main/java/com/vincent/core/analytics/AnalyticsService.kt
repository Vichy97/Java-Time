package com.vincent.core.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*

class AnalyticsService(private val firebaseAnalytics: FirebaseAnalytics) {

    private val eventProperties = mutableMapOf<String, String>()
    private val locale = Locale.getDefault()

    fun addUserProperty(key: String, value: String) {
        firebaseAnalytics.setUserProperty(key, value)
    }

    fun removeUserProperty(key: String) {
        firebaseAnalytics.setUserProperty(key, null)
    }

    fun addEventProperty(property: Property, value: String): AnalyticsService {
        val propertyName = property.name.toLowerCase(locale)
        eventProperties[propertyName] = value
        return this
    }

    fun trackEvent(event: Event) {
        val propertyBundle = getPropertyBundle()
        val eventName = event.name.toLowerCase(locale)
        firebaseAnalytics.logEvent(eventName, propertyBundle)
        eventProperties.clear()
    }

    fun trackPage(page: Page) {
        val propertyBundle = getPropertyBundle()
        val eventName = "page_view: ${page.name.toLowerCase(locale)}"
        firebaseAnalytics.logEvent(eventName, propertyBundle)
        eventProperties.clear()
    }

    private fun getPropertyBundle(): Bundle {
        val propertyBundle = Bundle()
        eventProperties.keys.forEach {
            propertyBundle.putString(it, eventProperties[it])
        }
        return propertyBundle
    }
}