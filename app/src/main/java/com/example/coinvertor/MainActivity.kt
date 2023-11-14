package com.example.coinvertor

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.coinvertor.databinding.ActivityMainBinding
import okhttp3.Address
import okhttp3.Headers
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    // api for country list
    private lateinit var binding: ActivityMainBinding
    private lateinit var countryList: MutableList<Country>
    private lateinit var currencyList: List<Currency>
    private lateinit var currencyCodes: List<String>
    private val rvCountry get() = binding.countryList
    private lateinit var adapter: CountryAdapter
    private val etFirstCurrency get() = binding.etFirstCurrency
    private val btnConvert get() = binding.btnConvert
    private val tvAutoCompleteFirstCountry get() = binding.tvAutoCompleteFirstCountry
    private var selectedFirstCountry = ""
    private val tvAutoCompleteSecondCountry get() = binding.tvAutoCompleteSecondCountry
    private var selectedSecondCountry = ""
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

        setOnCountrySelectedListener()
        setOnConvertClickedListener()
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

    private fun setOnCountrySelectedListener(){
        tvAutoCompleteFirstCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                selectedFirstCountry = editable.toString()
            }
        })

        tvAutoCompleteSecondCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                selectedSecondCountry = editable.toString()
            }
        })
    }

    private fun setOnConvertClickedListener() {
        btnConvert.setOnClickListener {
            convert()
        }
    }

    private fun convert() {
        val client = AsyncHttpClient()
        val amount = BigDecimal(etFirstCurrency.text.toString())

        client["https://api.getgeoapi.com/v2/currency/convert" +
                "?api_key=06a05ff7963d1a714518316857b352a6a03b893c" +
                "&from=${selectedFirstCountry}" +
                "&to=${selectedSecondCountry}" +
                "&amount=${amount}" +
                "&format=json", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val conversion = json.jsonObject.getJSONObject("rates").getJSONObject(selectedSecondCountry)["rate_for_amount"]
                etSecondCurrency.text = "$conversion"
                Log.d("Conversion", "conversion output: $conversion")
                Log.d("Conversion", "Response successful")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Conversion Error", errorResponse)
            }
        }]

    }

}
