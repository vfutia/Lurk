package com.vfutia.lurk.extension

import android.content.SharedPreferences

const val PREFERENCES_NAME = "lurk_shared_preferences"

private const val KEY_ACCESS_TOKEN = "access_token"

fun SharedPreferences.putToken(token: String) = edit().putString(KEY_ACCESS_TOKEN, token).commit()
fun SharedPreferences.getToken(): String? = getString(KEY_ACCESS_TOKEN, null)