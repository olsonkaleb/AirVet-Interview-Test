package com.olsonkaleb.airvetinterviewtest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olsonkaleb.airvetinterviewtest.databinding.FragmentUserProfileInformationBinding

class UserProfileInformationFragment : Fragment() {
    private var _binding: FragmentUserProfileInformationBinding? = null
    private val binding get() = _binding!!

    private lateinit var name: String
    private lateinit var gender: String
    private lateinit var age: String
    private lateinit var birthday: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name").toString()
            gender = it.getString("gender").toString()
            age = it.getString("age").toString()
            birthday = it.getString("birthday").toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserProfileInformationBinding.inflate(inflater, container, false)

        binding.userProfileInformationNameTextView.text = name
        binding.userProfileInformationGenderTextView.text = gender
        binding.userProfileInformationAgeTextView.text = age
        binding.userProfileInformationBirthdayTextView.text = birthday

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, gender: String, age: String, birthday: String) = UserProfileInformationFragment().apply {
            arguments = Bundle().apply {
                putString("name", name)
                putString("gender", gender)
                putString("age", age)
                putString("birthday", birthday)
            }
        }
    }
}