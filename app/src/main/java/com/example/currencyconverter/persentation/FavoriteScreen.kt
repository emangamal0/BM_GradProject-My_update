package com.example.currencyconverter.persentation

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import androidx.compose.material3.Divider
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.currencyconverter.data.local.dp.CachedCurrency
import com.example.currencyconverter.ui.CurrencyViewModel

@Preview
@Composable
fun FavoriteScreen (viewModel :CurrencyViewModel = viewModel() ){

    var showDialog by rememberSaveable {mutableStateOf(false)}
    var addToList: Boolean by rememberSaveable{mutableStateOf(false)}
    val favList = viewModel.remoteCurrencies.observeAsState()

    Box {
        Column(modifier = Modifier.background(color = Color.White)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "live exchange rates",
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(start = 30.dp, top = 15.dp),
                    style = TextStyle(fontWeight = FontWeight.Companion.Bold, fontSize = 15.sp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {showDialog = true},
                    modifier = Modifier.padding(end = 30.dp),
                    border = (BorderStroke(1.dp, Color.Black)),
                    colors = ButtonDefaults.buttonColors(Color.White)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Add to Favorite", color = Color.Black,
                        style = TextStyle(fontFamily = FontFamily.Serif),
                        modifier = Modifier.height(25.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "My portofoil",
                modifier = Modifier
                    .height(30.dp)
                    .padding(start = 30.dp),
                style = TextStyle(fontFamily = FontFamily.Serif, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 30.dp)
            ) {
                Column (modifier = Modifier.fillMaxHeight()){
                    favList.value?.filter{it.isFavourite}?.forEach{
                        AddList(item = it)
                    }
//                    if (addToList){
//                        AddList()
//                    }
                }
            }
        }
        if (showDialog) {
            Dialog(onDismissRequest = {showDialog = false}) {//column card cotain text lazy
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Row {
                        IconButton(onClick = {showDialog = false},
                            colors = IconButtonDefaults.iconButtonColors(Color.White),
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .border(BorderStroke(0.dp, Color.White), shape = CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clear),
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .background(Color.White)
                    )
                    Card(modifier = Modifier) {
                        Text(
                            text = "My  Favorites",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        )
                        LazyColumn(modifier = Modifier.background(color = Color.White)) {
                            itemsIndexed(favList.value?: listOf()) { index, item ->
                                var checkBox: Boolean by rememberSaveable{mutableStateOf(item.isFavourite)}
                                Row {
                                    AddList(item)
                                    Checkbox(
                                        checked = checkBox,
                                        onCheckedChange = {
                                            checkBox = it
                                           viewModel.saveItemInDB(item,it)
//                                           addToList = true
                                            // if (it)
                                            // viewmodel.savedatabase(item)
                                            // else
                                           // viewmodel.remvoedatabase(item)
                                        },
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape),
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color.Black,
                                            uncheckedColor = Color.LightGray
                                        )
                                    )

                                }
                                Divider(
                                    modifier = Modifier.height(1.dp),
                                    color = Color.LightGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddList (item: CachedCurrency) {
    Row(
        modifier = Modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(model = Uri.parse(item.flagUrl) , contentDescription = "",
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(Modifier.weight(1F)) {
            Text(text = item.code)
            Text(text =item.desc, color = Color.LightGray)
        }
    }
}