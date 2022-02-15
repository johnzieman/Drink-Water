package com.johnzieman.ziemapp.drinkwater.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.ui.viewmodels.WaterMainViewModel
import com.johnzieman.ziemapp.drinkwater.databinding.FragmentWaterMainBinding
import com.johnzieman.ziemapp.drinkwater.interfaces.OnCheckRegistration
import com.johnzieman.ziemapp.drinkwater.interfaces.OnOtherDrinksItemClicked
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily
import com.johnzieman.ziemapp.drinkwater.ui.adapters.OtherDrinkAdapter
import com.johnzieman.ziemapp.drinkwater.ui.adapters.OtherDrinkInMLAdapter


private const val TAG = "WATERMAIN"

class WaterMain : Fragment() {
    private lateinit var binding: FragmentWaterMainBinding
    private lateinit var adapter: OtherDrinkAdapter
    private lateinit var adapterInMl: OtherDrinkInMLAdapter
    lateinit var waterDaily: WaterDaily
    private var onCheckRegistration: OnCheckRegistration? = null


    private val waterMainViewModel: WaterMainViewModel by lazy {
        ViewModelProvider(this).get(WaterMainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCheckRegistration = context as OnCheckRegistration
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWaterMainBinding.inflate(layoutInflater, container, false)
        waterMainViewModel.getUsers().observe(
            viewLifecycleOwner
        ) { it ->
            val result = it
            if (result.isEmpty()) {
                onCheckRegistration?.onOpenLaunchFragment()
                Log.d(TAG, getString(R.string.db_is_empty))
            } else {
                Log.d(TAG, getString(R.string.db_is_not_empty))
                waterMainViewModel.getDays().observe(viewLifecycleOwner) { waterDaily ->
                    this@WaterMain.waterDaily = waterDaily[0]
                    showWaterProgress(
                        if (waterDaily[0].drunk < 1) 0.1f else waterDaily[0].drunk,
                        waterDaily[0].dailyRate
                    )
                    val currentWaterInPercent =
                        (waterDaily[0].drunk / waterDaily[0].dailyRate * 100)
                    binding.waterPercent.text =
                        String.format("%.1f", currentWaterInPercent) + "%"
                }
                waterMainViewModel.getUsers().observe(viewLifecycleOwner) {
                    binding.userName.text = "Hi, ${it[0].userName}"
                }

            }
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addWater.setOnClickListener {
            waterDaily.drunk += waterDaily.dailyRate / waterDaily.cupsRate
            waterDaily.cupDrunk += 1
            Log.d(TAG, waterDaily.drunk.toString())
            Log.d(TAG, waterDaily.cupDrunk.toString())
            waterMainViewModel.updateDay(waterDaily = waterDaily)
            playWaterAnimation()
        }

        binding.removeWater.setOnClickListener {
            Log.d(TAG, waterDaily.cupDrunk.toString())
            if (waterDaily.cupDrunk > 0) {
                waterDaily.drunk -= waterDaily.dailyRate / waterDaily.cupsRate
                waterDaily.cupDrunk -= 1
                Log.d(TAG, waterDaily.drunk.toString())
                waterMainViewModel.updateDay(waterDaily = waterDaily)
            } else {
                Log.d(TAG, "Wrong expression!")
            }

        }

        adapter = OtherDrinkAdapter(object : OnOtherDrinksItemClicked {
            override fun onClick() {
                adapterInMl = OtherDrinkInMLAdapter()
                adapterInMl.portion = listOf(
                    resources.getString(R.string.ml50),
                    resources.getString(R.string.ml100),
                    resources.getString(R.string.ml150),
                    resources.getString(R.string.ml200),
                    resources.getString(R.string.ml250),
                    resources.getString(R.string.ml300),
                    resources.getString(R.string.ml350),
                    resources.getString(R.string.ml400),
                    resources.getString(R.string.ml450),
                    resources.getString(R.string.ml500)
                )
                binding.recyclerView.adapter = adapterInMl
            }
        })
        adapter.drinks = listOf(
            R.drawable.one,
            R.drawable.three,
            R.drawable.eight,
            R.drawable.ten,
            R.drawable.eleven,
            R.drawable.twelve,
            R.drawable.two,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,

            R.drawable.nine

        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.recyclerView.adapter = adapter

    }


    private fun showWaterProgress(waterDrinked: Float, waterToBeDrinked: Float) {
        val wheelIndicatorView = binding.wheelIndicatorView
        val percentageOfExerciseDone = (waterDrinked / waterToBeDrinked * 100).toInt()
        wheelIndicatorView.filledPercent = percentageOfExerciseDone
        val bikeActivityIndicatorItem =
            WheelIndicatorItem(waterDrinked, Color.parseColor("#2BB5FF"))
        wheelIndicatorView.addWheelIndicatorItem(bikeActivityIndicatorItem)
        wheelIndicatorView.startItemsAnimation()
    }


    private fun playWaterAnimation() {
        val animator = ValueAnimator.ofFloat(0f, 0.5f)
        animator.addUpdateListener {
            binding.animationView.progress = animator.animatedValue as Float
        }
        animator.start()
    }

    override fun onDetach() {
        super.onDetach()
        onCheckRegistration = null
    }
}