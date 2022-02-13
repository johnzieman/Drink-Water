package com.johnzieman.ziemapp.drinkwater.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnzieman.ziemapp.drinkwater.databinding.OtherDrinksItemBinding

private const val TAG = "OtherDrinkAdapter"

class OtherDrinkAdapter : RecyclerView.Adapter<OtherDrinkAdapter.OtherDrinksView>() {
    var drinks: List<Int> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherDrinksView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OtherDrinksItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener {
            Log.d(TAG, "Works!")
        }
        return OtherDrinksView(binding)
    }

    override fun onBindViewHolder(holder: OtherDrinksView, position: Int) {
        holder.binding.drinkItem.setImageResource(drinks[position])
    }

    override fun getItemCount(): Int = drinks.size

    inner class OtherDrinksView(val binding: OtherDrinksItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}