package com.himelz.expensetracker.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.himelz.expensetracker.data.dao.ExpenseDao
import com.himelz.expensetracker.data.model.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private const val DATABASE_NAME = "expense_db"

        @JvmStatic
        fun getInstance(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(
                context,
                ExpenseDatabase::class.java,
                DATABASE_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    initBasicData(context)
                }

                fun initBasicData(context: Context) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val expenseDao = getInstance(context).expenseDao()
                        val sampleExpenses = listOf(
                            ExpenseEntity(
                                id = 1,
                                title = "Netflix",
                                amount = 100.0,
                                type = "Expense",
                                date = System.currentTimeMillis()
                            ),
                            ExpenseEntity(
                                id = 2,
                                title = "Upwork",
                                amount = 500.0,
                                type = "Income",
                                date = System.currentTimeMillis()
                            ),
                            ExpenseEntity(
                                id = 3,
                                title = "Spotify",
                                amount = 50.0,
                                type = "Expense",
                                date = System.currentTimeMillis()
                            ),
                            ExpenseEntity(
                                id = 4,
                                title = "Amazon",
                                amount = 250.0,
                                type = "Expense",
                                date = System.currentTimeMillis()
                            ),
                            ExpenseEntity(
                                id = 5,
                                title = "Paypal",
                                amount = 700.0,
                                type = "Income",
                                date = System.currentTimeMillis()
                            ),
                            ExpenseEntity(
                                id = 6,
                                title = "Youtube",
                                amount = 75.0,
                                type = "Expense",
                                date = System.currentTimeMillis()
                            )
                        )

                        // Insert the dummy data into the database
                        sampleExpenses.forEach {
                            expenseDao.insertExpense(it)
                        }
                    }
                }
            }).build()
        }

    }
}