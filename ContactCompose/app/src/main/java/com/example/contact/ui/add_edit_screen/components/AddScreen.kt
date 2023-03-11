package com.example.contact.ui.add_edit_screen.components

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.contact.ui.add_edit_screen.AddEditEvent
import com.example.contact.ui.add_edit_screen.AddViewModel
import com.example.contact.ui.util.DefaultImage
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddScreen(
    navController: NavController,
    viewModel: AddViewModel = hiltViewModel()
) {
    val contactName = viewModel.contactName.value
    val contactNumber = viewModel.contactNumber.value
    val contactEmail = viewModel.contactEmail.value
    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                viewModel.onEvent(AddEditEvent.PickedImage(uri, context))
            }
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddViewModel.UiEvent.SaveContact -> {
                    navController.navigateUp()
                }
                is AddViewModel.UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditEvent.SaveContact)
            }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Add Contact")
            }
        },
        modifier = Modifier.padding(bottom = 80.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add Contacts", style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        photoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                viewModel.contactImage.value?.let { byteArray ->
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(158.dp)
                                .clip(CircleShape)
                        )
                    } ?: run {
                        DefaultImage(size = 158)
                    }
                } ?: run {
                    DefaultImage(size = 158)
                }
            }
            Box(
                modifier = Modifier
                    .padding(end = 55.dp)
                    .align(Alignment.End)
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(AddEditEvent.IsFavourite)
                    },
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = if (viewModel.isFavourite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (viewModel.isFavourite.value) "Unlike" else "Like",
                        tint = if (viewModel.isFavourite.value) Color.Red else Color.Gray,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = com.example.contact.R.drawable.default_image),
                    contentDescription = "Phone",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(25.dp))
                EditTextField(
                    value = contactName.hint,
                    string = contactName.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditEvent.EnteredName(it))
                    },
                    keyboardType = KeyboardType.Text
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_call),
                    contentDescription = "Phone",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(25.dp))
                EditTextField(
                    value = contactNumber.hint,
                    string = contactNumber.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditEvent.EnteredPhoneNumber(it))
                    },
                    keyboardType = KeyboardType.Phone
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, end = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_dialog_email),
                    contentDescription = "Email",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(25.dp))
                EditTextField(
                    value = contactEmail.hint,
                    string = contactEmail.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditEvent.EnteredEmail(it))
                    },
                    keyboardType = KeyboardType.Email
                )
            }
        }
    }
}

@Composable
fun EditTextField(
    value: String, string: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType
) {
    TextField(
        value = string,
        onValueChange = onValueChange,
        label = { Text(text = value) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}