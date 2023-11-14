package com.example.coinvertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


class MainActivity : AppCompatActivity() {
    private lateinit var countryList: MutableList<Country>
    private lateinit var rvCountry: RecyclerView
    private lateinit var adapter: CountryAdapter

    data class Country(
        val name: String,
        val capital: String,
        val population: Int,
        val flagUrl: String,
        // Add other properties as needed
    )

        // private late init var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            //binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(R.layout.activity_main)

            rvCountry = findViewById(R.id.country_list)
            countryList = mutableListOf()

            adapter = CountryAdapter(countryList)

            rvCountry.adapter = adapter
            rvCountry.layoutManager = LinearLayoutManager(this)

            getCountryImageURL()

        }
    private fun getCountryImageURL() {
        val client = AsyncHttpClient()


        client["https://restcountries.com/v3.1/currency/cop", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val countryArray = json.jsonArray

                for (i in 0 until countryArray.length()) {
                    val countryObject = countryArray.getJSONObject(i)

                    val name = countryObject.getJSONObject("name").getString("common")
                    val capital = countryObject.getJSONArray("capital").getString(0)
                    val population = countryObject.getInt("population")
                    val flagUrl = countryObject.getJSONObject("flags").getString("png")

                    countryList.add(Country(name, capital, population, flagUrl))

                }
                Log.d("Country", "Number of countries: ${countryList.size}")

                // Check if the adapter is null or empty before updating it
                if (::adapter.isInitialized) {

                    Log.d("Country", "Adapter updated")
                } else {
                    Log.e("Country", "Adapter not initialized")
                }

                Log.d("Country", "Response successful")

                val adapter = CountryAdapter(countryList)
                rvCountry.adapter = adapter
                rvCountry.layoutManager = LinearLayoutManager(this@MainActivity)
                Log.d("Country", "response successful")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Country Error", errorResponse)
            }
        }]

    }

    }


    /*private fun setupClickListeners(){
        compute.setOnClickListener { computeButtonClicked() }
    }

    private fun computeButtonClicked() {
        try {
            val result = numberInput.pow(powerInput)
            output.text = result.toString()
        } catch (e: NumberFormatException) {
            output.text = "Invalid input" // Handle invalid input gracefully
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        runningCountJob?.cancel()
    }*/
