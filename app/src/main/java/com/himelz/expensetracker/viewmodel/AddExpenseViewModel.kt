package com.himelz.expensetracker.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himelz.expensetracker.data.ExpenseDatabase
import com.himelz.expensetracker.data.dao.ExpenseDao
import com.himelz.expensetracker.data.model.ExpenseEntity

class AddExpenseViewModel(private val dao: ExpenseDao) : ViewModel() {

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean {
        try {
            dao.insertExpense(expenseEntity)
            return true
        } catch (ex: Throwable) {
            return false
        }
    }

    private val _selectedChip = mutableStateOf("")
    val selectedChip: State<String> = _selectedChip

    fun selectChip(chip: String) {
        _selectedChip.value = chip
    }

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    fun setTitle(title: String) {
        _title.value = title
    }
}

class AddExpenseViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            val dao = ExpenseDatabase.getInstance(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpenseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}