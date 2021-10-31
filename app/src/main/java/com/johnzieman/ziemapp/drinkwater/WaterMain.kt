package com.johnzieman.ziemapp.drinkwater

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.johnzieman.ziemapp.drinkwater.interfaces.OnCheckRegistration

private const val TAG = "WATERMAIN"

class WaterMain : Fragment() {
    private lateinit var lottieAnimationView: LottieAnimationView

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_water_main, container, false)


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
        lottieAnimationView = view.findViewById(R.id.animationView)
        lottieAnimationView.cancelAnimation()
//        button.setOnClickListener {
//            val animator = ValueAnimator.ofFloat(0f, 0.5f)
//            animator.addUpdateListener {
//                lottieAnimationView.progress = animator.animatedValue as Float
//            }
//            animator.start()
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDetach() {
        super.onDetach()
        onCheckRegistration = null
    }

}