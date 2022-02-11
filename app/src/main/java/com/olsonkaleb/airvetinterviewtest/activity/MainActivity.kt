package com.olsonkaleb.airvetinterviewtest.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.olsonkaleb.airvetinterviewtest.data.UserProfileListViewModel
import com.olsonkaleb.airvetinterviewtest.R
import com.olsonkaleb.airvetinterviewtest.network.RandomUserApiClient
import com.olsonkaleb.airvetinterviewtest.fragment.UserListFragment
import com.olsonkaleb.airvetinterviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserProfileListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_activity_fragment_container, UserListFragment())
                .commit()

            RandomUserApiClient(this).getRandomUsers(50, {
                profiles -> viewModel.profiles.value = profiles
            }, {
                Toast.makeText(this, R.string.api_error, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val profileLocation = LatLng(viewModel.selectedUserProfile.value!!.latitude.toDouble(), viewModel.selectedUserProfile.value!!.longitude.toDouble())
        googleMap.addMarker(MarkerOptions().position(profileLocation))
        googleMap.setPadding(0, 500, 0, 0)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(profileLocation))
    }
}