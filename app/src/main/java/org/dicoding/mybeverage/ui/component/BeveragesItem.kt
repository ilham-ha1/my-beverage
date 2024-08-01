package org.dicoding.mybeverage.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.dicoding.mybeverage.data.model.Beverage
import org.dicoding.mybeverage.ui.theme.MyBeverageTheme

@Composable
fun BeveragesItem(
    beverage: Beverage,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = beverage.photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = beverage.name,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = beverage.flavor,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BeveragesItemPreview(){
    MyBeverageTheme {
        Beverage(
            1,
            "Water",
            "https://tandobeverage.com/wp-content/uploads/2021/03/water.jpg",
            "Transparent",
            "Fresh",
            "Water"
        )
    }
}