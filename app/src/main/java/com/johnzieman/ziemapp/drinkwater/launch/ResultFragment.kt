package com.johnzieman.ziemapp.drinkwater.launch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.WaterApplication.Companion.prefs
import com.johnzieman.ziemapp.drinkwater.interfaces.OnSaveUserResult
import com.johnzieman.ziemapp.drinkwater.launch.viewModels.LaunchConfigurationViewModel
import com.johnzieman.ziemapp.drinkwater.launch.viewModels.ResultViewModel
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily

class ResultFragment : Fragment() {
    private lateinit var textView3: TextView
    private lateinit var button: Button

    private var onSafeUserResult: OnSaveUserResult? = null

    private val viewModel: ResultViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onSafeUserResult = context as OnSaveUserResult
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        textView3 = view.findViewById(R.id.textView3)
        button = view.findViewById(R.id.button)
        viewModel.getUsers().observe(
            viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    val name = it[0].userName

                    val weight =
                        if (it[0].weightMetrics == "kg") it[0].userWeight.toDouble() * 2.20462.toDouble() else it[0].userWeight.toDouble()
                    calculateWater(name, weight)
                }
            }
        )

        button.setOnClickListener {
            onSafeUserResult?.onLaunchMainFragment()
        }

        return view
    }

    private fun calculateWater(userName: String, userWeight: Double) {
        Log.d("workingWeight", userWeight.toString())
        val resultWater = when {
            userWeight < 100.0 -> 67
            userWeight <= 100.0 -> 67
            userWeight <= 110.0 -> 74
            userWeight <= 120.0 -> 80
            userWeight <= 130.0 -> 87
            userWeight <= 140.0 -> 94
            userWeight <= 150.0 -> 100
            userWeight <= 160.0 -> 107
            userWeight <= 170.0 -> 114
            userWeight <= 180.0 -> 121
            userWeight <= 190.0 -> 127
            userWeight <= 200.0 -> 134
            userWeight <= 210.0 -> 141
            userWeight <= 220.0 -> 148
            userWeight <= 230.0 -> 154
            userWeight <= 240.0 -> 161
            userWeight <= 250.0 -> 168
            userWeight <= 260.0 -> 175
            userWeight <= 270.0 -> 182
            userWeight <= 280.0 -> 189
            userWeight <= 290.0 -> 196
            userWeight <= 300.0 -> 203
            userWeight <= 310.0 -> 210
            userWeight <= 320.0 -> 217
            userWeight <= 330.0 -> 224
            userWeight <= 340.0 -> 231
            userWeight <= 350.0 -> 238
            userWeight <= 360.0 -> 245
            userWeight <= 370.0 -> 252
            userWeight <= 380.0 -> 259
            userWeight <= 390.0 -> 266
            userWeight <= 400.0 -> 273
            else -> 275
        }
        val waterGlassCount = (resultWater / 8)
        val calculatedOzToMl = resultWater.toFloat() * 29.5735
        textView3.text = getString(
            R.string.per_day_water_result,
            userName,
            resultWater.toInt().toString(),
            calculatedOzToMl.toInt().toString(),
            waterGlassCount.toInt().toString()
        )
        val waterDaily =
            WaterDaily(
                dailyRate = calculatedOzToMl.toFloat(),
                drunk = 0.1.toFloat(),
                cupsRate = waterGlassCount.toInt(),
                cupDrunk = 0,
                otherDrinks = 0.0.toFloat(),
                lastTimeDrankAnyWater = NOINFO,
                lastTimeDrankAnyWaterReservation = NOINFO
            )
        viewModel.addDay(waterDaily)
        prefs.push(ML, "250ml ")
        Log.d("workingWeight", resultWater.toString())
    }

    override fun onDetach() {
        super.onDetach()
        onSafeUserResult = null
    }


    companion object{
        private const val NOINFO = "No info yet"
        private const val ML = "ml"
    }

}