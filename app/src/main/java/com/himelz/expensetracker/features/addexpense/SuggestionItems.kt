package com.himelz.expensetracker.features.addexpense

import com.himelz.expensetracker.R

data class SuggestionItems(
    val name: String,
    val iconResId: Int
)

// List of suggestions
val suggestions = listOf(
    SuggestionItems("Netflix", R.drawable.ic_netflix),
    SuggestionItems("Spotify", R.drawable.ic_spotify),
    SuggestionItems("Amazon", R.drawable.ic_amazon),
    SuggestionItems("Paypal", R.drawable.ic_paypal),
    SuggestionItems("Youtube", R.drawable.ic_youtube),
    SuggestionItems("Upwork", R.drawable.ic_upwork),
    SuggestionItems("Other", R.drawable.ic_dollar)
)
