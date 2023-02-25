package com.example.contact.ui.favorite_contact

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contact.R
import com.example.contact.model.Contact
import com.example.contact.ui.all_contact_screen.components.BottomSheetElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {
    val contactList = viewModel.contactsList
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
                }

                )
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
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(118.dp)
                        .clip(CircleShape)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.default_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(118.dp)
                        .clip(CircleShape)
                        .padding(16.dp)
                )
            }
            Text(
                text = contact.name,
                modifier = Modifier.paddingFromBaseline(top = 12.dp, bottom = 6.dp)
            )
        }
    }
}

