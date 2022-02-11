package com.olsonkaleb.airvetinterviewtest.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserProfileListViewModel : ViewModel() {
    val profiles: MutableLiveData<List<UserProfile>> = MutableLiveData()
    val selectedUserProfile: MutableLiveData<UserProfile> = MutableLiveData()
}