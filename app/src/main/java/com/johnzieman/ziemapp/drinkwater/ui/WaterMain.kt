package com.johnzieman.ziemapp.drinkwater.ui

import android.animation.ValueAnimator
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
import com.johnzieman.ziemapp.drinkwater.viewmodels.WaterMainViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWaterMainBinding.inflate(layoutInflater, container, false)
        waterMainViewModel.getUsers().observe(
            viewLifecycleOwner, Observer {
                val result = it
                if (result.isEmpty()) {
                    onCheckRegistration?.onOpenLaunchFragment()
                    Log.d(TAG, "Database is empty")
                } else {
                    Log.d(TAG, "Database is not empty")




                }
            }
        )



        binding.addWater.setOnClickListener {
            val animator = ValueAnimator.ofFloat(0f, 0.5f)
            animator.addUpdateListener {
                binding.animationView.progress = animator.animatedValue as Float
            }
            animator.start()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wheelIndicatorView = binding.wheelIndicatorView

        val waterToBeDrunk = 4.0f
        val waterDrunk = 3.0f
        val percentageOfExerciseDone = (waterDrunk / waterToBeDrunk * 100).toInt()
        wheelIndicatorView.filledPercent = percentageOfExerciseDone
        val bikeActivityIndicatorItem = WheelIndicatorItem(waterDrunk, Color.parseColor("#2BB5FF"))
        wheelIndicatorView.addWheelIndicatorItem(bikeActivityIndicatorItem)
        wheelIndicatorView.startItemsAnimation()

    }

    override fun onDetach() {
        super.onDetach()
        onCheckRegistration = null
    }

}