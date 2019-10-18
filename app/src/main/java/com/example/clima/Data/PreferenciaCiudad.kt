package com.example.clima.Data

import android.app.Activity
import android.content.SharedPreferences

class PreferenciaCiudad(activity: Activity) {

    var prefs: SharedPreferences

    init {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE)
    }

     var ciudad: String
    get() = prefs.getString("ciudad", "Guatemala")!!
    set(ciudad) = prefs.edit().putString("ciudad", ciudad).apply()


}