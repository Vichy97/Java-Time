package com.vincent.ui.about

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.core.text.bold
import com.vincent.core.ui.BaseFragment
import com.vincent.ui.R

import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        setupDevelopedByText()
        setupContactText()
    }

    private fun setupDevelopedByText() {
        val developedBy = getString(R.string.developed_by)
        val developer = getString(R.string.developer)

        val spannable = SpannableStringBuilder()
            .bold { append(developedBy) }
            .append(" $developer")

        tv_developed_by.text = spannable
    }

    private fun setupContactText() {
        val contact = getString(R.string.contact)
        val email = getString(R.string.email)

        val spannable = SpannableStringBuilder()
            .bold { append(contact) }
            .append(" $email")

        tv_contact.text = spannable
    }
}