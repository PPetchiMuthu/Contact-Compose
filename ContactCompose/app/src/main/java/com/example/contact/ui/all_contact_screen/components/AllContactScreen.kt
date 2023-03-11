package com.example.contact.ui.all_contact_screen.components

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contact.R
import com.example.contact.model.Contact
import com.example.contact.ui.util.BottomBarScreen
import com.example.contact.ui.util.DefaultImage

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter",
    "UnusedMaterial3ScaffoldPaddingParameter",
    "CoroutineCreationDuringComposition"
)
@Composable
fun AllContactScreen(
    viewModel: ContactViewModel,
    navController: NavHostController
) {
    val contactListState by viewModel.contactsList.collectAsState()
    val contactList: List<Contact> = contactListState.data ?: emptyList()
    var bottomSheetVisible by remember { mutableStateOf(false) }
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = skipHalfExpanded)
    val contact = remember {
        mutableStateOf<Contact?>(null)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(BottomBarScreen.AddEdit.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "All Contacts")
            }
        },
        modifier = Modifier.padding(bottom = 80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Contacts", style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(contactList) { contacts: Contact ->
                    ContactCard(contact = contacts, onDeleteClick = {
//                        viewModel.deleteContact(context, contacts.phoneNumber.toString())
                    }, onContactClick = {
                        contact.value = contacts
                        bottomSheetVisible = true
                    })
                }
            }
        }
    }
    if (bottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                bottomSheetVisible = false
            }, sheetState = bottomSheetState
        ) {
            contact.value?.let {
                BottomSheetElement(it)
            }
        }
    }
}

@Composable
fun BottomSheetElement(contact: Contact) {
    Column(modifier = Modifier.padding(32.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            contact.photo?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                bitmap?.let {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                    )
                } ?: run {
                    DefaultImage(size = 54)
                }
            }?: run {
                DefaultImage(size = 54)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = contact.name,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {}) {
                Icon(
                    imageVector = if (contact.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (contact.isFavorite) "Unlike" else "Like",
                    tint = if (contact.isFavorite) Color.Red else Color.Gray
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = contact.phoneNumber.toString(),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = contact.emailAddress ?: "No Email Id",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }
    }
}

@Composable
fun ContactCard(
    contact: Contact,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onContactClick: () -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.surface)
        .clickable {
            onContactClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            contact.photo?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                bitmap?.let {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                    )
                } ?: run {
                    DefaultImage(size = 54)
                }
            }?: run {
                DefaultImage(size = 54)
            }
            Text(
                text = contact.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = onDeleteClick, modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    TextField(value = "", onValueChange = {}, placeholder = {
        Text("Search")
    }, leadingIcon = {
        Icon(Icons.Default.Search, contentDescription = null)
    }, colors = TextFieldDefaults.textFieldColors(
        contentColorFor(backgroundColor = MaterialTheme.colorScheme.surface)
    ), modifier = modifier
        .heightIn(60.dp)
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
    )
}