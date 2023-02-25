package com.example.contact.ui.add_edit_screen.components

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contact.ui.add_edit_screen.AddViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel()
) {
    val contactName = viewModel.contactName.value
    val contactNumber = viewModel.contactNumber.value
    val contactEmail = viewModel.contactEmail.value
    Column(
        verticalArrangement = Arrangement.Center, modifier = Modifier.padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            viewModel.contactImage.value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape)
                )
            } ?: run {
                Image(
                    painter = painterResource(id = R.drawable.ic_menu_report_image),
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape)
                )
            }
            EditTextField(
                value = contactName.hint,
                string = contactName.text,
                onValueChange = {},
                keyboardType = KeyboardType.Text
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = if (viewModel.isFavourite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (viewModel.isFavourite.value) "Unlike" else "Like",
                    tint = if (viewModel.isFavourite.value) Color.Red else Color.Gray
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_call),
                contentDescription = "Phone"
            )
            Spacer(modifier = Modifier.width(16.dp))
            EditTextField(
                value = contactNumber.hint,
                string = contactNumber.text,
                onValueChange = {},
                keyboardType = KeyboardType.Phone
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_dialog_email),
                contentDescription = "Email"
            )
            Spacer(modifier = Modifier.width(16.dp))
            EditTextField(
                value = contactEmail.hint,
                string = contactEmail.text,
                onValueChange = {},
                keyboardType = KeyboardType.Email
            )
        }
    }
}

@Composable
fun EditTextField(
    value: String, string: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = string) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

