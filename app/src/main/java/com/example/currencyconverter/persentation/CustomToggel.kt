package com.example.currencyconverter.persentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToggleButton(onConvertClick :() -> Unit , onCompareClick :() ->Unit) {

   Card {
        Row(
            modifier = Modifier
                .width(270.dp)
                .height(50.dp)
                .clip(shape = RoundedCornerShape(24.dp))
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(text = "convert",
                style = TextStyle(fontSize = 15.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { onConvertClick() })
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Compare",
                style = TextStyle(fontSize = 15.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { onCompareClick()})
            )
        }
//        Box {
//            if (clicedConvert) {
//                ConvertCard()
//            } else
//                CompareCard()
//        }
    }
}
