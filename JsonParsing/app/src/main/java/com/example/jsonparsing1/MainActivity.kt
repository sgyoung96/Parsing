package com.example.jsonparsing1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    val info: String = "[{'name':'홍길동','age':12,'address':'서울'}," + "{'name':'청길동','age':34,'address':'대전'}," + "{'name':'백길동','age':56,'address':'대구'}]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvText.text = info

        btnParsing.setOnClickListener { JSONParse(info) }
    }

    fun JSONParse(jsonStr: String) {
        val stringBuilder = StringBuilder()

        try {
            val jsonArray = JSONArray(jsonStr)
            for ( i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                var name: String = jsonObject.getString("name")
                var age: Int = jsonObject.getInt("age")
                var address: String = jsonObject.getString("address")

                stringBuilder.append("이름: ").append(name).append(" 나이: ").append(age).append(" 주소: ").append(address).append("\n")
            }
            tvText.text = stringBuilder
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}