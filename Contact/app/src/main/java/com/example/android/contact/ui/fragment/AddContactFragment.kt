package com.example.android.contact.ui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.android.contact.R
import com.example.android.contact.databinding.FragmentAddContactBinding
import com.example.android.contact.db.Contact
import com.example.android.contact.ui.ContactViewModel
import kotlinx.coroutines.launch


class AddContactFragment : Fragment() {

    private lateinit var binding: FragmentAddContactBinding

    private lateinit var viewModel: ContactViewModel

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        binding.ok.setOnClickListener {
            lifecycleScope.launch {
                insertDataToDatabase()
            }
        }

        val pickImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            // Do something with the selected image URI
            Glide.with(this)
                .load(uri)
                .transform(CircleCrop())
                .into(binding.addImages)
            imageUri = uri
        }

        binding.addImages.setOnClickListener {
            // To open the image gallery, use the following code:
            pickImage.launch("image/*")
        }
        return binding.root
    }

    private suspend fun insertDataToDatabase() {
        val name: String? = binding.addName.text.toString()
        val phoneNo1: String? = binding.addPhoneNo1.text.toString()
        val phoneNo2: String? = binding.addPhoneNo2.text.toString()
        val phoneNo3: String? = binding.addPhoneNo3.text.toString()
        val email: String? = binding.addEmail.text.toString()
        val dob: String? = binding.addDob.text.toString()

        val fav = if (binding.switchFav.isChecked) 1 else 0
        val group: String? = binding.addGroup.selectedItem.toString()

        if (inputCheck(name, phoneNo1, email, dob, group)) {
            val contact =
                Contact(0, name, phoneNo1, phoneNo2, phoneNo3, email, dob, fav, group, getBitMap())
            viewModel.addContact(contact)
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_navigation_addContact_to_navigation_all_contact)
        } else {
            Toast.makeText(requireContext(), "Please Fill", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun getBitMap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(imageUri)
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }


    private fun inputCheck(
        name: String?,
        phoneNo1: String?,
        email: String?,
        dob: String?,
        group: String?
    ): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(phoneNo1) && TextUtils.isEmpty(email) && TextUtils.isEmpty(
            dob
        ) && TextUtils.isEmpty(group))
    }

}