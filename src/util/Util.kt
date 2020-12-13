package com.lc.server.util

import com.google.gson.Gson

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)
