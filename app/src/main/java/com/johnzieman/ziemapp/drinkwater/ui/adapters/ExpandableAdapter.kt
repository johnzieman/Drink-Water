package com.johnzieman.ziemapp.drinkwater.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnzieman.ziemapp.drinkwater.databinding.DrinkHistoryItemBinding
import com.johnzieman.ziemapp.drinkwater.models.DailyStory

private const val TAG = "ExpandableAdapter"

class ExpandableAdapter() :
    RecyclerView.Adapter<ExpandableAdapter.ExpandableView>() {
    private lateinit var binding: DrinkHistoryItemBinding
    var drinks: List<DailyStory> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableView {
        val inflater = LayoutInflater.from(parent.context)
        binding = DrinkHistoryItemBinding.inflate(inflater, parent, false)
        return ExpandableView(binding)
    }

    override fun onBindViewHolder(holder: ExpandableView, position: Int) {
        holder.bind(drinks[position])
    }

    override fun getItemCount(): Int = drinks.size

    inner class ExpandableView(val binding: DrinkHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(element: DailyStory) {
            binding.drinkImage.setImageResource(element.logo)
            binding.drinkWeight.text = element.howMuchList.toString()
            binding.drinkTime.text = element.timeList
        }

    }
}