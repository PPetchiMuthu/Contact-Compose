package com.example.android.contact.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.contact.ui.ContactViewModel
import com.example.android.contact.adapter.ContactAdapter
import com.example.android.contact.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        val adapter = ContactAdapter()
        val recyclerView = binding.favRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readFavContact.observe(viewLifecycleOwner, Observer { user ->
            adapter.differ.submitList(user)
        })

        return binding.root
    }


}