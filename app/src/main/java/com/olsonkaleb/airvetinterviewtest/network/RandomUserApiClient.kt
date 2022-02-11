package com.olsonkaleb.airvetinterviewtest.network

import android.content.Context
import android.net.Uri
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.olsonkaleb.airvetinterviewtest.data.UserProfile
import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.*

class RandomUserApiClient(context: Context) {
    private val queue: RequestQueue = Volley.newRequestQueue(context)
    private val randomUserBaseUrl = "https://randomuser.me/api/"

    fun getRandomUsers(userCount: Int, successCallback: (List<UserProfile>) -> Unit, errorCallback: () -> Unit) {
        val builtUri: Uri = Uri.parse(randomUserBaseUrl).buildUpon()
            .appendQueryParameter("results", userCount.toString())
            .appendQueryParameter("nat", "us")
            .build()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, builtUri.toString(), null,
            { response ->
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapter(UserProfile::class.java, deserializer)
                val stringReader = StringReader(response.getJSONArray("results").toString())
                val list = gsonBuilder.create().fromJson(stringReader, Array<UserProfile>::class.java).toList()
                successCallback.invoke(list)
            }, {
                errorCallback.invoke()
            }
        )
        queue.add(jsonObjectRequest)
    }

    private var deserializer: JsonDeserializer<UserProfile> = JsonDeserializer<UserProfile> { json, _, _ ->
        val jsonObject = json.asJsonObject
        val dobObject = jsonObject.getAsJsonObject("dob")
        val locationObject = jsonObject.getAsJsonObject("location")
        val date = SimpleDateFormat( "yyyy-MM-dd", Locale.US).parse(dobObject.get("date").asString)!!
        var birthdayDay = SimpleDateFormat( "d", Locale.US).format(date)
        birthdayDay += when (birthdayDay.toInt()) {
            1, 21, 31 -> "st"
            2, 22 -> "nd"
            3, 23 -> "rd"
            else -> "th"
        }

        val profile = UserProfile()
        profile.fullName = jsonObject.getAsJsonObject("name").get("first").asString + " " + jsonObject.getAsJsonObject("name").get("last").asString
        profile.gender = jsonObject.get("gender").asString.replaceFirstChar { it.uppercase() }
        profile.age = dobObject.get("age").asString
        profile.birthday = SimpleDateFormat( "MMMM ", Locale.US).format(date) + birthdayDay
        profile.email = jsonObject.get("email").asString
        profile.phone = jsonObject.get("phone").asString
        profile.cell = jsonObject.get("cell").asString
        profile.street = locationObject.getAsJsonObject("street").get("number").asString + " " + locationObject.getAsJsonObject("street").get("name").asString
        profile.city = locationObject.get("city").asString
        profile.state = locationObject.get("state").asString
        profile.latitude = locationObject.getAsJsonObject("coordinates").get("latitude").asString
        profile.longitude = locationObject.getAsJsonObject("coordinates").get("longitude").asString
        profile.avatarUrl = jsonObject.getAsJsonObject("picture").get("large").asString
        profile
    }
}