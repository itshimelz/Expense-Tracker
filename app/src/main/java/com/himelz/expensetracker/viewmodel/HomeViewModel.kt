package com.himelz.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himelz.expensetracker.R
import com.himelz.expensetracker.data.ExpenseDatabase
import com.himelz.expensetracker.data.dao.ExpenseDao
import com.himelz.expensetracker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao) : ViewModel() {
    val expenses = dao.getAllExpenses()

    fun getBalance(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            if (it.type == "Income") {
                total += it.amount
            } else {
                total -= it.amount
            }
        }
        return "$ $total"
    }

    fun getTotalIncome(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            if (it.type == "Income") {
                total += it.amount

            }
        }
        return "$ $total"

    }

    fun getTotalExpense(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            if (it.type == "Expense") {
                total += it.amount

            }

        }
        return "$ $total"
    }

    fun getItemIcon(title: String): Int {
        when (title) {
            "Netflix" -> return R.drawable.ic_netflix
            "Spotify" -> return R.drawable.ic_spotify
            "Amazon" -> return R.drawable.ic_amazon
            "Youtube" -> return R.drawable.ic_youtube
            "Upwork" -> return R.drawable.ic_upwork
            "Paypal" -> return R.drawable.ic_paypal
        }
        return R.drawable.ic_expenses
    }
}


class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpenseDatabase.getInstance(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}