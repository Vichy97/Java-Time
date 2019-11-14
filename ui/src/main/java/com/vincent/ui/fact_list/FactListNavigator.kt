package com.vincent.ui.fact_list

import android.content.Intent
import com.vincent.core.ui.BaseNavigator
import com.vincent.core.utils.RxProvider
import com.vincent.ui.R

private const val storeUrl = "https://play.google.com/store/apps/details?id=com.vincent.javatime"

internal class FactListNavigator(rxProvider: RxProvider) : BaseNavigator(rxProvider) {

    fun showSuggestionDialog() {
        navigate(R.id.action_factListFragment_to_suggestionDialogFragment)
    }

    fun navigateToAbout() {
        navigate(R.id.action_factListFragment_to_aboutFragment)
    }

    fun showShareSheet() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, storeUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        navigate(shareIntent)
    }
}