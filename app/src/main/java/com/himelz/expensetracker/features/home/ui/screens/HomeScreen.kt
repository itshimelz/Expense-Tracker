package com.himelz.expensetracker.features.home.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.himelz.expensetracker.R
import com.himelz.expensetracker.ui.theme.Zinc

@Composable
fun HomeScreen() {
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
            CardItem(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            TransactionList(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints

                    }
            )
        }
    }
}

@Composable
fun CardItem(modifier: Modifier = Modifier) {
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
                    text = "$5000",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_dots),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
                    .size(20.dp)

            )

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
                amount = "$2464"
            )

            CardRowItem(
                modifier = Modifier.align(alignment = Alignment.CenterEnd),
                title = "Expense",
                image = R.drawable.ic_expenses,
                amount = "$1355"
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
fun TransactionList(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
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

        // Sample transactions data
        val transactions = listOf(
            Transaction("Upwork", R.drawable.ic_upwork, "+ $850.00", Zinc, "Today"),
            Transaction("Spotify", R.drawable.ic_spotify, "- $85.00", Color.Red, "Yesterday"),
            Transaction("Paypal", R.drawable.ic_paypal, "+ $1,406.00", Zinc, "Jan 30, 2022"),
            Transaction("Youtube", R.drawable.ic_youtube, "- $11.99", Color.Red, "Jan 16, 2022"),
            Transaction("Netflix", R.drawable.ic_netflix, "- $15.99", Color.Red, "Aug 10, 2023"),
            Transaction("Amazon", R.drawable.ic_amazon, "+ $320.00", Zinc, "Jul 25, 2023"),
        )

        // Display each transaction item
        transactions.forEach { transaction ->
            TransactionItem(transaction)
            Divider(
                color = Color.Gray,
                modifier = Modifier.alpha(.3f)
            )
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
    ) {
        Image(
            painter = painterResource(id = transaction.icon),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .align(alignment = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.name,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = transaction.date,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Text(
            text = transaction.amount,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = transaction.amountColor,
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