package com.example.android.contact.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.android.contact.databinding.FragmentViewContactBinding

class ViewContactFragment : Fragment() {

    private val args by navArgs<ViewContactFragmentArgs>()

    private var _binding: FragmentViewContactBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addName.text = args.currentContact.name
        binding.addPhoneNo1.text = args.currentContact.phoneNum1
        binding.addPhoneNo2.text = args.currentContact.phoneNum2 ?: ""
        binding.addPhoneNo3.text = args.currentContact.phoneNum3 ?: ""
        binding.addEmail.text = args.currentContact.emailId
        binding.addDob.text = args.currentContact.dob
        binding.addGroup.text = args.currentContact.groupContact
        Glide.with(this)
            .load(args.currentContact.image)
            .transform(CircleCrop())
            .into(binding.addImages)
        binding.fab.setOnClickListener {
            val action =
                ViewContactFragmentDirections.actionNavigationViewContactToNavigationUpdateFragment(
                    args.currentContact
                )
            binding.fab.findNavController().navigate(action)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}