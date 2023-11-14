package com.example.coinvertor

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.coinvertor.databinding.ActivityMainBinding
import okhttp3.Headers


class MainActivity : AppCompatActivity() {
    // api for country list
    private lateinit var binding: ActivityMainBinding
    private lateinit var countryList: MutableList<Country>
    private lateinit var currencyList: List<Currency>
    private lateinit var currencyCodes: List<String>
    private val rvCountry get() = binding.countryList
    private lateinit var adapter: CountryAdapter
    private val etFirstCurrency get() = binding.etFirstCurrency
    private val spnFirstCountry get() = binding.spnFirstCountry
    private val btnConvert get() = binding.btnConvert
    private val spnSecondCountry get() = binding.spnSecondCountry
    private val tvAutoCompleteFirstCountry get() = binding.tvAutoCompleteFirstCountry
    private val tvAutoCompleteSecondCountry get() = binding.tvAutoCompleteSecondCountry
    private val etSecondCurrency get() = binding.etSecondCurrency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countryList = mutableListOf()
        currencyCodes = mutableListOf()

        adapter = CountryAdapter(countryList)

        rvCountry.adapter = adapter
        rvCountry.layoutManager = LinearLayoutManager(this)

        getCountryImageURL()
        getCurrencyList(this)

        Log.d("Currency", "Number of currency codes here: ${currencyCodes.size}")
    }

    private fun getCurrencyList(context: Context) {
        val client = AsyncHttpClient()

        client["https://api.getgeoapi.com/v2/currency/list?api_key=06a05ff7963d1a714518316857b352a6a03b893c&format=json", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val currencies = json.jsonObject.getJSONObject("currencies")
                val currencyMap = mutableMapOf<String, String>()
                currencies.keys().forEach { key ->
                    currencyMap[key] = currencies[key].toString()
                }
                currencyList = currencyMap.map {(code, name) -> Currency(code, name)}
                currencyCodes = currencyMap.map {(code) -> code}
                Log.d("Currency", "Number of currencies: ${currencyList.size}")
                Log.d("Currency", "Number of currency codes: ${currencyCodes.size}")
                Log.d("Currency", "Response successful")

                val adapter = CurrencyAdapter(context, currencyCodes)
                tvAutoCompleteFirstCountry.setAdapter(adapter)
                tvAutoCompleteSecondCountry.setAdapter(adapter)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Currency Error", errorResponse)
            }
        }]
    }

    private fun getCountryImageURL() {
        val client = AsyncHttpClient()


        client["https://restcountries.com/v3.1/all", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val countryArray = json.jsonArray

                for (i in 0 until countryArray.length()) {
                    val countryObject = countryArray.getJSONObject(i)

                    try {
                        val name = countryObject.getJSONObject("name").getString("common")
                        val capital = countryObject.getJSONArray("capital").getString(0)
                        val population = countryObject.getInt("population")
                        val flagUrl = countryObject.getJSONObject("flags").getString("png")

                        countryList.add(Country(name, capital, population, flagUrl))
                    } catch (_: Exception) {
                        continue
                    }
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
