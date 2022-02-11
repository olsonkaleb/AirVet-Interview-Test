package com.olsonkaleb.airvetinterviewtest.data

import java.io.Serializable

class UserProfile : Serializable {
    lateinit var avatarUrl: String
    lateinit var fullName: String
    lateinit var age: String
    lateinit var birthday: String
    lateinit var gender: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var cell: String
    lateinit var street: String
    lateinit var city: String
    lateinit var state: String
    lateinit var latitude: String
    lateinit var longitude: String
}