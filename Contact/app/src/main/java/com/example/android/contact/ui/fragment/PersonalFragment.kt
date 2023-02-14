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
import com.example.android.contact.databinding.FragmentPersonalBinding

class PersonalFragment : Fragment() {

    private lateinit var binding: FragmentPersonalBinding

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        val adapter = ContactAdapter()
        val recyclerView = binding.recyclerPersonal
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readPersonalContact.observe(viewLifecycleOwner, Observer {
            adapter.differ.submitList(it)
        })

        return binding.root
    }
}