package com.johnzieman.ziemapp.drinkwater

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView


class WaterMain : Fragment() {
    private lateinit var button: Button
    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_water_main, container, false)
        button = view.findViewById(R.id.button)
        lottieAnimationView = view.findViewById(R.id.animationView)
        lottieAnimationView.cancelAnimation()
        button.setOnClickListener {
            val animator = ValueAnimator.ofFloat(0f, 0.5f)
            animator.addUpdateListener {
                lottieAnimationView.progress = animator.animatedValue as Float
            }
            animator.start()
        }

        return view
    }

}