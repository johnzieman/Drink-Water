package com.johnzieman.ziemapp.drinkwater.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnzieman.ziemapp.drinkwater.databinding.OtherDrinksItemBinding
import com.johnzieman.ziemapp.drinkwater.databinding.OtherDrinksItemInMlBinding

private const val TAG = "OtherDrinkInMLAdapter"
class OtherDrinkInMLAdapter: RecyclerView.Adapter<OtherDrinkInMLAdapter.OtherDrinksInMlView>() {
    var portion: List<String> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherDrinksInMlView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OtherDrinksItemInMlBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener {
            Log.d(TAG, "Works!")

        }
        return OtherDrinksInMlView(binding)
    }

    override fun onBindViewHolder(holder: OtherDrinksInMlView, position: Int) {
        holder.binding.drinkItemInMl.text = portion[position]
    }

    override fun getItemCount(): Int = portion.size

    inner class OtherDrinksInMlView(val binding: OtherDrinksItemInMlBinding) :
        RecyclerView.ViewHolder(binding.root)
}