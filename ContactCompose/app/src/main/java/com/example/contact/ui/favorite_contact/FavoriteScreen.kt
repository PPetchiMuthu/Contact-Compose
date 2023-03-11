package com.example.contact.ui.favorite_contact

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.contact.model.Contact
import com.example.contact.ui.all_contact_screen.components.BottomSheetElement
import com.example.contact.ui.all_contact_screen.components.ContactViewModel
import com.example.contact.ui.util.DefaultImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(viewModel: ContactViewModel) {
    val contactListState by viewModel.favoriteContactsList.collectAsState()
    val contactList: List<Contact> = contactListState
    var bottomSheetVisible by remember { mutableStateOf(false) }
    val skipHalfExpanded by remember { mutableStateOf(false) }
    val bottomSheetState = rememberSheetState(skipHalfExpanded = skipHalfExpanded)
    val contact = remember {
        mutableStateOf<Contact?>(null)
    }
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
                text = "Favorite Contacts", style = MaterialTheme.typography.headlineMedium
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(contactList) { contacts: Contact ->
                RoundedImage(contact = contacts, onContactClick = {
                    contact.value = contacts
                    bottomSheetVisible = true
                })
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
fun RoundedImage(contact: Contact, modifier: Modifier = Modifier, onContactClick: () -> Unit) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.surface)
        .clickable {
            onContactClick()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            contact.photo?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                bitmap?.let {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(118.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    DefaultImage(size = 118)
                }
            }?: run {
                DefaultImage(size = 118)
            }
            Text(
                text = contact.name,
                modifier = Modifier.paddingFromBaseline(top = 12.dp, bottom = 6.dp)
            )
        }
    }
}