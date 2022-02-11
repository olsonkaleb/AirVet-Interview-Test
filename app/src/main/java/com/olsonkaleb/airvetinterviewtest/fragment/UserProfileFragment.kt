package com.olsonkaleb.airvetinterviewtest.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.olsonkaleb.airvetinterviewtest.R
import com.olsonkaleb.airvetinterviewtest.data.UserProfile
import com.olsonkaleb.airvetinterviewtest.databinding.FragmentUserProfileBinding
import com.squareup.picasso.Picasso

class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userProfile: UserProfile

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        userProfile = arguments?.getSerializable("profile") as UserProfile
        bindProfileData()

        val bounceAnimation = ScaleAnimation(0.3f, 1f, 0.3f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        bounceAnimation.interpolator = OvershootInterpolator()
        bounceAnimation.duration = 300
        binding.profileAvatarCardView.startAnimation(bounceAnimation)

        binding.profileInformationViewPager.isUserInputEnabled = false
        binding.profileInformationViewPager.adapter = ProfileInformationAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.profileInformationViewPager) { tab, position ->
            when (position) {
                0 -> { tab.text = getString(R.string.user_profile_info_tab_title)}
                1 -> { tab.text = getString(R.string.user_profile_contact_tab_title)}
                2 -> { tab.text = getString(R.string.user_profile_location_tab_title)}
            }
        }.attach()

        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentItem = binding.profileInformationViewPager.currentItem
        binding.profileInformationViewPager.setCurrentItem(0, false)
        binding.profileInformationViewPager.setCurrentItem(currentItem, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindProfileData() {
        Picasso.get().load(userProfile.avatarUrl).into(binding.profileAvatarImageView)
        binding.profileNameTextView.text = userProfile.fullName
    }

    companion object {
        @JvmStatic
        fun newInstance(userProfile: UserProfile?) = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putSerializable("profile", userProfile)
            }
        }
    }

    inner class ProfileInformationAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> UserProfileInformationFragment.newInstance(userProfile.fullName, userProfile.gender, userProfile.age, userProfile.birthday)
                1 -> UserProfileContactFragment.newInstance(userProfile.email, userProfile.phone, userProfile.cell)
                2 -> UserProfileLocationFragment.newInstance(userProfile.street, userProfile.city, userProfile.state)
                else -> UserProfileInformationFragment()
            }
        }
    }
}