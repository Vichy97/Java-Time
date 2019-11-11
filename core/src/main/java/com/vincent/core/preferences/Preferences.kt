package com.vincent.core.preferences

import android.content.SharedPreferences

private const val KEY_CURRENT_PAGE = "KEY_CURRENT_PAGE"

class Preferences(private val sharedPreferences: SharedPreferences) {

    fun putCurrentPage(currentPage: Int) {
        sharedPreferences.edit()
            .putInt(KEY_CURRENT_PAGE, currentPage)
            .apply()
    }

    fun getCurrentPage(): Int {
        return sharedPreferences.getInt(KEY_CURRENT_PAGE, 0)
    }
}