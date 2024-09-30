package com.himelz.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himelz.expensetracker.R
import com.himelz.expensetracker.Utils
import com.himelz.expensetracker.data.ExpenseDatabase
import com.himelz.expensetracker.data.dao.ExpenseDao
import com.himelz.expensetracker.data.model.ExpenseEntity

class StatisticsViewModel(dao: ExpenseDao) : ViewModel() {
}


class StatisticsViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            val dao = ExpenseDatabase.getInstance(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return StatisticsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}