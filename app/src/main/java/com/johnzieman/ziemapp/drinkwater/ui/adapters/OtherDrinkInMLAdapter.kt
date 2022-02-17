package com.johnzieman.ziemapp.drinkwater.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnzieman.ziemapp.drinkwater.databinding.OtherDrinksItemBinding
import com.johnzieman.ziemapp.drinkwater.databinding.OtherDrinksItemInMlBinding
import com.johnzieman.ziemapp.drinkwater.interfaces.OnOtherDrinksCountClicked

private const val TAG = "OtherDrinkInMLAdapter"
class OtherDrinkInMLAdapter(private val onOtherDrinksCountClicked: OnOtherDrinksCountClicked) :
    RecyclerView.Adapter<OtherDrinkInMLAdapter.OtherDrinksInMlView>() {
    private lateinit var binding: OtherDrinksItemInMlBinding
    var portion: List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherDrinksInMlView {
        val inflater = LayoutInflater.from(parent.context)
        binding = OtherDrinksItemInMlBinding.inflate(inflater, parent, false)
        return OtherDrinksInMlView(binding)
    }

    override fun onBindViewHolder(holder: OtherDrinksInMlView, position: Int) {
        holder.binding.drinkItemInMl.text = portion[position]
        binding.root.setOnClickListener {
            val waterMl = portion[position].replace("ml", "").toFloat()
            onOtherDrinksCountClicked.onClick(waterMl)
        }
    }

    override fun getItemCount(): Int = portion.size

    inner class OtherDrinksInMlView(val binding: OtherDrinksItemInMlBinding) :
        RecyclerView.ViewHolder(binding.root)
}