package org.dicoding.mybeverage.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import org.dicoding.mybeverage.R
import org.dicoding.mybeverage.data.repository.BeverageRepository
import org.dicoding.mybeverage.ui.common.UiState
import org.dicoding.mybeverage.ui.screen.home.HomeViewModel
import org.dicoding.mybeverage.ui.theme.MyBeverageTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    id: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = koinViewModel()
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getBeverageById(id)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.photoUrl,
                    data.name,
                    data.flavor,
                    data.color,
                    data.ingredient,
                    onBackClick = navigateBack,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    image: String,
    name: String,
    flavor:String,
    color:String,
    ingredient:String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box{
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray))
                Text(
                    text = "Flavor: $flavor",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
                Text(
                    text = "Color: $color",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
                Text(
                    text = "Ingredient: $ingredient",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview(){
    MyBeverageTheme {
        DetailContent(
            image = "https://tandobeverage.com/wp-content/uploads/2021/03/water.jpg",
            name = "Water",
            flavor = "Fresh",
            color = "Transparent",
            ingredient = "Water",
            onBackClick = {})
    }
}
