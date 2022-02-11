package com.olsonkaleb.airvetinterviewtest.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.olsonkaleb.airvetinterviewtest.data.UserProfile
import com.olsonkaleb.airvetinterviewtest.data.UserProfileListViewModel
import com.olsonkaleb.airvetinterviewtest.R
import com.olsonkaleb.airvetinterviewtest.data.UserProfileListAdapter
import com.olsonkaleb.airvetinterviewtest.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserProfileListViewModel by activityViewModels()
    private lateinit var listAdapter: UserProfileListAdapter

    private enum class FilterMode {
        Male,
        Female,
        UnderFifty,
        OverFifty,
        None
    }
    private var currentFilter = FilterMode.None

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profiles.observe(viewLifecycleOwner) {
            if (viewModel.profiles.value != null) {
                binding.profileList.layoutManager = LinearLayoutManager(requireActivity())
                listAdapter = UserProfileListAdapter { profile -> onProfileSelected(profile) }
                refreshProfilesWithFilter()
                binding.profileList.adapter = listAdapter
            }
            binding.userProfileListLoadingBar.visibility = View.GONE
        }

        binding.filterUsersFab.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle(R.string.filter_dialog_title).setItems(R.array.filter_options) { _, which ->
                currentFilter = FilterMode.values()[which]
                refreshProfilesWithFilter()
            }.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun refreshProfilesWithFilter() {
        if (viewModel.profiles.value == null)
            return

        val filteredProfiles = ArrayList<UserProfile>()
        for (profile in viewModel.profiles.value!!) {
            if (currentFilter == FilterMode.Male && profile.gender == "Male"
                || currentFilter == FilterMode.Female && profile.gender == "Female"
                || currentFilter == FilterMode.UnderFifty && profile.age.toInt() < 50
                || currentFilter == FilterMode.OverFifty && profile.age.toInt() >= 50
                || currentFilter == FilterMode.None)
                filteredProfiles.add(profile)
        }

        listAdapter.setItems(filteredProfiles)
    }

    private fun onProfileSelected(userProfile: UserProfile?) {
        viewModel.selectedUserProfile.value = userProfile
        parentFragmentManager.commit {
            setCustomAnimations(
                R.anim.profile_enter_animation,
                R.anim.profile_enter_animation,
                R.anim.profile_exit_animation,
                R.anim.profile_exit_animation
            )
            add(R.id.main_activity_fragment_container, UserProfileFragment.newInstance(userProfile))
            addToBackStack(null)
        }
    }
}