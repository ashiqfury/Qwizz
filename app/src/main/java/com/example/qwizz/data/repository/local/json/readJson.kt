package com.example.qwizz.data.repository.local.json

import android.content.Context
import com.example.qwizz.model.Question
import com.example.qwizz.model.QuestionResponse
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import org.json.JSONObject
import java.io.InputStream


internal fun Context.readJSONFromAsset(vararg fileName: String?): ArrayList<JSONObject>? {
    val resultJson = ArrayList<JSONObject>()
    for (i in fileName.indices) {
        this.readJSONFromAsset(fileName[i])?.let {
            resultJson.add(it)
        }
    }
    return resultJson.takeIf { it.isNotEmpty() }
}

internal fun Context.readJSONFromAsset(fileName: String?): JSONObject? {
    return try {
        val inputStream: InputStream = assets.open(fileName?:return null)
        JSONObject(inputStream.bufferedReader().use{it.readText()})
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}

fun Context.getDataFromAssets(fileName: String): List<Question> {
    val gson = Gson()

    val type = object : TypeToken<QuestionResponse>() {}.type
    val qnString = assets.open(fileName).bufferedReader().use { it.readText() }
    val result = gson.fromJson(qnString, type) as QuestionResponse
    return result.results
}