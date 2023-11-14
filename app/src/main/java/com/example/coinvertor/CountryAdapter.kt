package com.example.coinvertor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey

class CountryAdapter(private val countryList: MutableList<Country>): RecyclerView.Adapter<CountryAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryImage: ImageView  = view.findViewById(R.id.country_image)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countryList[position]
        Log.d("country","$country")
        Glide.with(holder.itemView)
            .load(country.flagUrl) // Assuming you have a property like "flagUrl" in your Country class
            .into(holder.countryImage)
    }

    override fun getItemCount() = countryList.size

}



