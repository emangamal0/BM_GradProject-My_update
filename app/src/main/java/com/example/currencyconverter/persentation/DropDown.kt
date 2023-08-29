package com.example.currencyconverter.persentation

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.CurrencyViewModel

@Composable
fun CurrencyDropdown(modifier: Modifier, viewModel: CurrencyViewModel = viewModel(), num: Int) {


    val list = viewModel.remoteCurrencies.observeAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedCurrencyIndex by remember { mutableStateOf(0) }

    val selectedCurrency = list.value?.get(selectedCurrencyIndex)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Column(
            Modifier
                .clickable { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedCurrency != null) {
                    AsyncImage(
                        model = Uri.parse(selectedCurrency.flagUrl), contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                    Text(text = selectedCurrency.code)
                }

                Spacer(modifier = Modifier.width(60.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Localized description"
                )
            }

            if (expanded) {
                Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))

                list.value?.forEachIndexed { index, cachedCurrency ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCurrencyIndex = index
                            expanded = false
                            viewModel.setDropDownState(cachedCurrency.code, num)
                        },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = Uri.parse(cachedCurrency.flagUrl),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 8.dp)
                                )
                                Text(text = cachedCurrency.code)
                            }
                        }
                    )
                }
            }

        }
    }
}