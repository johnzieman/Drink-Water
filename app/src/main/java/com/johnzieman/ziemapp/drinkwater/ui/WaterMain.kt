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
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.ui.viewmodels.WaterMainViewModel
import com.johnzieman.ziemapp.drinkwater.databinding.FragmentWaterMainBinding
import com.johnzieman.ziemapp.drinkwater.interfaces.OnCheckRegistration


private const val TAG = "WATERMAIN"

class WaterMain : Fragment() {
    private lateinit var binding: FragmentWaterMainBinding

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
            viewLifecycleOwner, Observer { it ->
                val result = it
                if (result.isEmpty()) {
                    onCheckRegistration?.onOpenLaunchFragment()
                    Log.d(TAG, getString(R.string.db_is_empty))
                } else {
                    Log.d(TAG, getString(R.string.db_is_not_empty))
                    waterMainViewModel.getDays().observe(viewLifecycleOwner) { waterDaily ->
                        showWaterProgress(
                            waterDaily[0].dailyRate.toFloat() / 8,
                            if (waterDaily[0].drunk.toFloat() < 1) 0.1f else waterDaily[0].drunk.toFloat()
                        )
                        binding.waterPercent.text = waterDaily[0].drunk.toString() + "%"
                    }
                    waterMainViewModel.getUsers().observe(viewLifecycleOwner) {
                        binding.userName.text = "Hi, ${it[0].userName}"
                    }

                }
            }
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var waterDrinked = 10.0.toFloat()

        binding.addWater.setOnClickListener {
//            waterMainViewModel.getDays().observe(viewLifecycleOwner) { waterDaily ->
                showWaterProgress(
                    waterDrinked,
                    waterDrinked - 1
                )
//            }
            playWaterAnimation()
        }
        binding.removeWater.setOnClickListener {

        }
    }


    private fun showWaterProgress(waterToBeDrinked: Float, waterDrinked: Float) {
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