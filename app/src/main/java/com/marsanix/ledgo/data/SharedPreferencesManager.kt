package com.marsanix.ledgo.data

import android.content.Context

const val NAME_PREF = "PREF_PRIVATE"
const val USER_NAME_KEY = "_USER_NAME"
const val IS_SUBMITTED_NAME_KEY = "_IS_SUBMITTED_NAME"

class SharedPreferencesManager(context: Context) {
    private val preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    var userName
        get() = preferences.getString(USER_NAME_KEY, "")
        set(value) {
            editor.putString(USER_NAME_KEY, value)
            editor.commit()
        }

    var isSubmittedName
        get() = preferences.getBoolean(IS_SUBMITTED_NAME_KEY, false)
        set(value) {
            editor.putBoolean(IS_SUBMITTED_NAME_KEY, value)
            editor.commit()
        }

    fun clear() {
        editor.clear()
        editor.commit()
    }

}