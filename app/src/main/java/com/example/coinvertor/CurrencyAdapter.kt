package com.example.coinvertor

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CurrencyAdapter(context: Context, countries: List<String>) :
    ArrayAdapter<String>(context, R.layout.custom_dropdown_item, countries) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = getItem(position)
        return view
    }
}