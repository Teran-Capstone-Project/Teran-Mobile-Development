package com.example.teran.ui.home_page.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.quote_provider.QuotesProvider
import java.util.Calendar

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ervan Fadillah"
    }
    val text: LiveData<String> = _text

    private val _quote = MutableLiveData<String>()

    private var lastUpdatedDate: Long = 0L

    fun updateQuote() {
        val today = Calendar.getInstance()
        val todayOfYear = today.get(Calendar.DAY_OF_YEAR)
        if (todayOfYear != lastUpdatedDate.toInt()) {
            lastUpdatedDate = todayOfYear.toLong()
            val rawQuote = QuotesProvider.getQuote()
            _quote.value = "\"$rawQuote\""

        }
    }

    init {
        // Initialize quote when the ViewModel is created
        updateQuote()
    }

    val quote: LiveData<String> = _quote
}