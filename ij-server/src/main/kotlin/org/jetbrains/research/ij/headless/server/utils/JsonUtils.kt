package org.jetbrains.research.ij.headless.server.utils

import com.google.gson.Gson

object JsonUtils {
    private val gson = Gson()

    val requestAdapter = gson.getAdapter(Code::class.java)
}
