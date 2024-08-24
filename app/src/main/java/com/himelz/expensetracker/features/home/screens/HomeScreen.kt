package com.himelz.expensetracker.features.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.himelz.expensetracker.R
import com.himelz.expensetracker.data.model.ExpenseEntity
import com.himelz.expensetracker.ui.theme.Zinc
import com.himelz.expensetracker.viewmodel.HomeViewModel
import com.himelz.expensetracker.viewmodel.HomeViewModelFactory

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, card, list, topBar) = createRefs()
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Good morning",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                    Text(
                        text = "Rahat H. Himel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { }
                        .clip(RoundedCornerShape(6.dp))
                        .size(32.dp)
                        .align(alignment = Alignment.CenterVertically)
                )
            }
            val state = homeViewModel.expenses.collectAsState(initial = emptyList())
            val balance = homeViewModel.getBalance(state.value)
            val income = homeViewModel.getTotalIncome(state.value)
            val expense = homeViewModel.getTotalExpense(state.value)

            CardItem(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                balance, income, expense
            )
            TransactionList(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    },
                list = state.value,
                viewModel = homeViewModel
            )
        }
    }
}

@Composable
fun CardItem(modifier: Modifier = Modifier, balance: String, income: String, expense: String) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .height(200.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Zinc)
            .padding(16.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.align(alignment = Alignment.CenterStart)) {
                Text(
                    text = "Total Balance",
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Text(
                    text = balance,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
//                    .padding(horizontal = 16.dp)
                    .align(alignment = Alignment.CenterEnd)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            CardRowItem(
                modifier = Modifier.align(alignment = Alignment.CenterStart),
                title = "Income",
                image = R.drawable.ic_income,
                amount = income
            )

            CardRowItem(
                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                title = "Expense",
                image = R.drawable.ic_expenses,
                amount = expense
            )

        }

    }
}

@Composable
fun CardRowItem(modifier: Modifier, title: String, image: Int, amount: String) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.size(6.dp))
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
        }
        Spacer(modifier = Modifier.size(1.dp))
        Text(
            text = amount,
            color = Color.White,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>, viewModel: HomeViewModel) {
    LazyColumn(modifier = modifier) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Transactions History",
                    color = Color.Black,
                    fontSize = 18.sp,
                )
                Text(
                    text = "See all",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .clickable {
                            // Handle "See all" click here
                        }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
        items(list){ item ->
            TransactionItem(
                title = item.title,
                date = item.date.toString(),
                amount = item.amount.toString(),
                type = item.type,
                icon = viewModel.getItemIcon(item.title)
            )
            Divider(
                color = Color.Gray,
                modifier = Modifier.alpha(.3f)
            )
        }
    }
}

@Composable
fun TransactionItem(title: String, date: String, amount: String, type: String, icon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .align(alignment = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = date,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Text(
            text = if (type == "Income") "+ $amount" else "- $amount",
            fontSize = 16.sp,
            color = if (type == "Income") Color.Green else Color.Red,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}