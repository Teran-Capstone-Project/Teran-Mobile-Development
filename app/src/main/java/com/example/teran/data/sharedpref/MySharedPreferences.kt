package com.example.teran.data.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.example.teran.R
import com.example.teran.data.model.User

class MySharedPreferences(context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(R.string.app_name.toString(), Context.MODE_PRIVATE)

    fun addUser(user: User) {
        val editor = sharedPref.edit()
        editor.putString("user_token", user.token)
        editor.putString("user_id", user.id)
        editor.putString("user_name", user.name)
        editor.putString("user_email", user.email)
        editor.putString("user_password", user.password)
        editor.putString("user_profilePicture", user.profilePicture)
        editor.putString("user_role", user.role)
        editor.apply()
    }

    fun updateName(newName: String) {
        val editor = sharedPref.edit()
        editor.putString("user_name", newName)
        editor.apply()
    }

    fun getUser(): User {
        val user = User()
        user.token = sharedPref.getString("user_token", null)
        user.id = sharedPref.getString("user_id", null)
        user.name = sharedPref.getString("user_name", null)
        user.email = sharedPref.getString("user_email", null)
        user.password = sharedPref.getString("user_password", null)
        user.profilePicture = sharedPref.getString("user_profilePicture", null)
        user.role = sharedPref.getString("user_role", null)
        return user
    }

    fun clear() {
        sharedPref
            .edit()
            .clear()
            .apply()
    }
}