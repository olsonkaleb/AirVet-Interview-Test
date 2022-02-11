package com.olsonkaleb.airvetinterviewtest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import com.olsonkaleb.airvetinterviewtest.R
import com.olsonkaleb.airvetinterviewtest.activity.MainActivity
import com.olsonkaleb.airvetinterviewtest.databinding.FragmentUserProfileLocationBinding

class UserProfileLocationFragment : Fragment() {
    private var _binding: FragmentUserProfileLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var street: String
    private lateinit var city: String
    private lateinit var state: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            street = it.getString("street").toString()
            city = it.getString("city").toString()
            state = it.getString("state").toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserProfileLocationBinding.inflate(inflater, container, false)

        binding.userProfileLocationAddressStreetTextView.text = street
        binding.userProfileLocationAddressCityTextView.text = city
        binding.userProfileLocationAddressStateTextView.text = state

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(activity as MainActivity)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(street: String, city: String, state: String) = UserProfileLocationFragment().apply {
            arguments = Bundle().apply {
                putString("street", street)
                putString("city", city)
                putString("state", state)
            }
        }
    }
}