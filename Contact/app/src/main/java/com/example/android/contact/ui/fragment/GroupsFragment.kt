package com.example.android.contact.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.contact.databinding.FragmentGroupsBinding

class GroupsFragment : Fragment() {

    private lateinit var binding: FragmentGroupsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)

        binding.cardFriends.setOnClickListener {
            val action =
                GroupsFragmentDirections.actionNavigationGroupsToNavigationViewGroup("Friends")
            binding.cardFriends.findNavController().navigate(action)
        }
        binding.cardFamily.setOnClickListener {
            val action =
                GroupsFragmentDirections.actionNavigationGroupsToNavigationViewGroup("Family")
            binding.cardFriends.findNavController().navigate(action)
        }
        binding.cardNeighbours.setOnClickListener {
            val action =
                GroupsFragmentDirections.actionNavigationGroupsToNavigationViewGroup("Neighbours")

            binding.cardFriends.findNavController().navigate(action)
        }
        binding.cardBusiness.setOnClickListener {
            val action =
                GroupsFragmentDirections.actionNavigationGroupsToNavigationViewGroup("Business")
            binding.cardFriends.findNavController().navigate(action)
        }
        binding.cardOffice.setOnClickListener {
            val action =
                GroupsFragmentDirections.actionNavigationGroupsToNavigationViewGroup("Office")
            binding.cardFriends.findNavController().navigate(action)
        }
        return binding.root
    }

}