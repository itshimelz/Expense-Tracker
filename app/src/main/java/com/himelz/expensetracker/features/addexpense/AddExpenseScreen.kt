package com.himelz.expensetracker.features.addexpense

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.himelz.expensetracker.R
import com.himelz.expensetracker.Utils.convertMillisToDate
import com.himelz.expensetracker.components.IncomeExpenseChips
import com.himelz.expensetracker.components.NameSelectionDropdown
import com.himelz.expensetracker.data.model.ExpenseEntity
import com.himelz.expensetracker.ui.theme.LightZinc
import com.himelz.expensetracker.viewmodel.AddExpenseViewModel
import com.himelz.expensetracker.viewmodel.AddExpenseViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AddExpenseScreen(navController: NavController) {

    val addExpenseViewModel: AddExpenseViewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)

    val coroutineScope = rememberCoroutineScope()

    Surface {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (card, topBar, titleBar) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .constrainAs(titleBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                IconButton(
                    onClick = {  },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(alignment = Alignment.CenterEnd)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_dots),
                        contentDescription = "Menu",
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Add Expense",
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.Center)
                )
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(alignment = Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            DataForm(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(titleBar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                viewModel = addExpenseViewModel,
                onAddExpenseAddClicked = {
                    coroutineScope.launch {
                        if (addExpenseViewModel.addExpense(it)) {
                            navController.popBackStack()
                        }
                    }
                }

            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataForm(
    modifier: Modifier,
    viewModel: AddExpenseViewModel,
    onAddExpenseAddClicked: (model: ExpenseEntity) -> Unit = {}
) {
    var amount by remember { mutableStateOf("") }
    var addButton by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(top = 60.dp)
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Name Input
            NameSelectionDropdown(viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            // Amount Input
            Text(
                text = "AMOUNT",
                color = Color.DarkGray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                placeholder = { Text("Amount") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.AttachMoney,
                        contentDescription = null,
                        tint = Color.Gray,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = LightZinc,
                    focusedBorderColor = LightZinc,
                    unfocusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedLeadingIconColor = LightZinc
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = VisualTransformation.None

            )
            Spacer(modifier = Modifier.height(16.dp))

            // Date Input
            Text(
                text = "DATE",
                color = Color.DarkGray,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { },
                placeholder = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = LightZinc,
                    focusedBorderColor = LightZinc,
                    unfocusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
                visualTransformation = VisualTransformation.None
            )

            if (showDatePicker) {
                Popup(
                    onDismissRequest = { showDatePicker = false },
                    alignment = Alignment.TopStart,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(elevation = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface)

                    ) {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Income and Expense chips
            IncomeExpenseChips(viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            // Add Invoice Button
            Button(
                onClick = {
                    val model = ExpenseEntity(
                        id = null,
                        title = viewModel.title.value,
                        amount = amount.toDouble(),
                        type = viewModel.selectedChip.value,
                        date = selectedDate
                    )
                    onAddExpenseAddClicked(model)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (viewModel.selectedChip.value == "Income") "Add Income" else "Add Expense",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

        }
    }

}


@Preview(
    showSystemUi = true
)
@Composable
fun AddExpensePreview() {
    AddExpenseScreen(rememberNavController())
}