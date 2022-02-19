package com.johnzieman.ziemapp.drinkwater.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnzieman.ziemapp.drinkwater.databinding.OtherDrinksItemInMlBinding
import com.johnzieman.ziemapp.drinkwater.interfaces.OnDrinksCountClicked

private const val TAG = "OtherDrinkInMLAdapter"

class DrinkInMLAdapter(private val onOtherDrinksCountClicked: OnDrinksCountClicked) :
    RecyclerView.Adapter<DrinkInMLAdapter.OtherDrinksInMlView>() {
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
        holder.bind(position)
    }

    override fun getItemCount(): Int = portion.size

    inner class OtherDrinksInMlView(val binding: OtherDrinksItemInMlBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var _position = 0

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(position: Int) {
            _position = position
        }

        override fun onClick(v: View?) {
            val waterMl = portion[_position].replace("ml", "").toFloat()
            Log.d(TAG, waterMl.toString())
            onOtherDrinksCountClicked.onClick(waterMl)
        }
    }
}