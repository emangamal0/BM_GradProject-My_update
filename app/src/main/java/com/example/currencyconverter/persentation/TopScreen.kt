package com.example.currencyconverter.persentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R

@Composable
fun TopApp() {
    Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(239.dp)

            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(239.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painterResource(id = R.drawable.background_image),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = "ConCurrency",
                    style = TextStyle(color = Color.White, fontSize = 24.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .padding(start = 20.dp, top = 40.dp)
                )

                Text(
                    text = "Currency Converter",
                    style = TextStyle(color = Color.White, fontSize = 25.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .padding(top = 45.dp)
                )

                Text(
                    text = "Check live foreign currency exchange rates",
                    style = TextStyle(color = Color.White, fontSize = 13.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .padding(top = 105.dp)
                )
            }

        }





    }

