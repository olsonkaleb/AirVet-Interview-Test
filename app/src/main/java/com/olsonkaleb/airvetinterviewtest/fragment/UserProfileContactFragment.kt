package com.olsonkaleb.airvetinterviewtest.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olsonkaleb.airvetinterviewtest.databinding.FragmentUserProfileContactBinding

class UserProfileContactFragment : Fragment() {
    private var _binding: FragmentUserProfileContactBinding? = null
    private val binding get() = _binding!!

    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var cell: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString("email").toString()
            phone = it.getString("phone").toString()
            cell = it.getString("cell").toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserProfileContactBinding.inflate(inflater, container, false)

        binding.userProfileContactEmailTextView.text = email
        binding.userProfileContactPhoneTextView.text = phone
        binding.userProfileContactCellphoneTextView.text = cell

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(email: String, phone: String, cell: String) = UserProfileContactFragment().apply {
            arguments = Bundle().apply {
                putString("email", email)
                putString("phone", phone)
                putString("cell", cell)
            }
        }
    }
}