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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
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
fun FavoriteScreen(viewModel: CurrencyViewModel = viewModel()) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    val favList = viewModel.myPorotfoilList.observeAsState()

    val remoteList = viewModel.remoteCurrencies.observeAsState().value?.map { remoteItem ->
        val itemFavorie = favList?.value?.find { it.code == remoteItem.code }?.isFavourite ?: false
        remoteItem.copy(isFavourite = itemFavorie)
    }

    val favRateList = viewModel.favoriteRatesList.value
    var i = 0

    val favListData = favList.value?.filter { it.isFavourite }
    

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
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(end = 30.dp),
                    border = (BorderStroke(1.dp, Color.Black)),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    favListData?.forEach {
                        AddList(item = it)
                        Spacer(modifier = Modifier
                            .height(3.dp))

                        Divider(modifier = Modifier
                            .height(1.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (favRateList.isNotEmpty()) {
                        favRateList.forEach {
                            Text(
                                text = it.rate.toString(),
                                modifier = Modifier.padding(vertical = 13.dp)
                            )
                        }
                    }
                }

            }
        }

        if (showDialog) {
            Dialog(onDismissRequest = {
                showDialog = false
            }) {//column card cotain text lazy
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Row {
                        IconButton(
                            onClick = { showDialog = false },
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
                    Card(modifier = Modifier.background(Color.White)) {
                        Text(
                            text = "My  Favorites",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(start = 10.dp)

                        )
                    Spacer(modifier = Modifier.height(10.dp).background(Color.White))
                        LazyColumn(modifier = Modifier.background(color = Color.White)) {
                            itemsIndexed(remoteList ?: listOf()) { index, item ->
                                var checkBox: Boolean by rememberSaveable { mutableStateOf(item.isFavourite) }
                                val icon=if(checkBox)Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Row(modifier =Modifier.weight(3f)) {
                                        AddList(item)
                                    }
                                    Row(modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.End) {
                                        IconButton(onClick = {
                                            checkBox = checkBox.not()
                                            viewModel.saveItemInDB(item, checkBox)
                                            viewModel.updateFavorite()
                                        } ,
                                            Modifier
                                                .padding(top = 8.dp)
                                                .size(20.dp)) {
                                            Icon(imageVector = icon, contentDescription = "" )
                                        }
                                    }
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
fun AddList(item: CachedCurrency) {
    Row(
        modifier = Modifier.padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = Uri.parse(item.flagUrl), contentDescription = "",
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(Modifier.weight(1F)) {
            Text(text = item.code)
            Text(text = item.desc, color = Color.LightGray)
        }

    }
}