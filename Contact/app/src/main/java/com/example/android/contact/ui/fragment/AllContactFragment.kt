package com.example.android.contact.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.contact.ui.ContactViewModel
import com.example.android.contact.R
import com.example.android.contact.adapter.ContactAdapter
import com.example.android.contact.databinding.FragmentAllContactsBinding
import com.google.android.material.snackbar.Snackbar


class AllContactFragment : Fragment() {

    private var _binding: FragmentAllContactsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ContactViewModel

    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        _binding = FragmentAllContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = ContactAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readAllContact.observe(viewLifecycleOwner, Observer { user ->
            adapter.differ.submitList(user)
        })

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("currentContact", it)
            }
            findNavController().navigate(
                R.id.action_navigation_all_contact_to_navigation_view_contact,
                bundle
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val contact = adapter.differ.currentList[position]
                viewModel.deleteContact(contact)
                Snackbar.make(binding.root, "Successfully deleted article", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.addContact(contact)
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }

        binding.searchBar.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchDatabase(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchDatabase(query)
                }
                return true
            }
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_all_contact_to_navigation_addContact)
        }

        return root
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchContact(searchQuery)
            .observe(/* owner = */ viewLifecycleOwner, /* observer = */
                Observer { user ->
                    adapter.differ.submitList(user)
                })
    }
}

