package com.example.gsonparsing1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20120101"
    val appHelper: AppHelper = AppHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etText.setText(url)
        btnRequest.setOnClickListener{ sendRequest() }

        if (appHelper.requestQueue == null) {
            appHelper.requestQueue = Volley.newRequestQueue(applicationContext)
        }
    }

    fun sendRequest() {
        val request: StringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String>() {
                override fun onResponse(response: String) {
                    tvText.text = response

                    parseResponse(response)
                }
            },
            Response.ErrorListener {
                fun onErrorResponse(error: VolleyError) {
                    tvText.text = error.message
                }
            },
            fun getParams: Map<String, String>() throw AuthFailureError {
                val params: Map<String, String> = HashMap<String, String>()
                return params
            }

        )

        request.setShouldCache(false)
        appHelper.requestQueue?.add(request)
    }


    fun parseResponse(response: String) {
        val gson = Gson()
        val movieList: MovieList = gson.fromJson(response, MovieList::class.java)

        if (movieList != null) {
            val cnt: Int = movieList.boxofficeResult.dailyBoxofficeList.size
            Toast.makeText(applicationContext, cnt.toString() + "movies found", Toast.LENGTH_SHORT).show()
        }
    }
}