package com.himelz.expensetracker.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himelz.expensetracker.R
import com.himelz.expensetracker.ui.theme.LightZinc
import com.himelz.expensetracker.ui.theme.Zinc
import com.himelz.expensetracker.viewmodel.AddExpenseViewModel

@Composable
fun IncomeExpenseChips(viewModel: AddExpenseViewModel) {

    Row {
        // Income Chip
        AssistChip(
            onClick = {
                viewModel.selectChip("Income")
            },
            label = { Text("Income") },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_income),
                    contentDescription = null,
                    colorFilter = if (viewModel.selectedChip.value == "Income") {
                        ColorFilter.tint(Zinc)
                    } else {
                        ColorFilter.tint(Color.Gray)
                    },
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = if (viewModel.selectedChip.value == "Income") LightZinc.copy(alpha = 0.2f) else Color.Unspecified,
                labelColor = if (viewModel.selectedChip.value == "Income") Zinc else Color.Gray,
            )
        )

        Spacer(modifier = Modifier.width(10.dp))

        // Expense Chip
        AssistChip(
            onClick = {
                viewModel.selectChip("Expense")
            },
            label = { Text("Expense") },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_expenses),
                    contentDescription = null,
                    colorFilter = if (viewModel.selectedChip.value == "Expense") {
                        ColorFilter.tint(Color.Red)
                    } else {
                        ColorFilter.tint(Color.Gray)
                    },
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = if (viewModel.selectedChip.value == "Expense") Color.Red.copy(alpha = 0.2f) else Color.Unspecified,
                labelColor = if (viewModel.selectedChip.value == "Expense") Color.Red else Color.Gray,
            )
        )
    }
}

