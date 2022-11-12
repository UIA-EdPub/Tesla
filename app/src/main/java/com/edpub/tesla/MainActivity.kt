package com.edpub.tesla

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.edpub.tesla.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        makeReq()
        binding.bMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun makeReq() {
        Log.i("fetchjson", "on")
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://power.larc.nasa.gov/api/temporal/daily/point?start=20220101&end=20220130&latitude=28.82899353809571&longitude=78.24633084540343&community=ag&parameters=T2M,ALLSKY_SFC_PAR_TOT&format=json&header=true&time-standard=lst"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("fetchjson", response.toString())

            },
            Response.ErrorListener {
                Log.i("fetchjson", it.toString())
            })
        queue.add(stringRequest)
    }

}