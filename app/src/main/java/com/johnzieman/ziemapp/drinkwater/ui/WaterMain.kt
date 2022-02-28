package com.johnzieman.ziemapp.drinkwater.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem
import com.google.android.material.card.MaterialCardView
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.WaterApplication.Companion.prefs
import com.johnzieman.ziemapp.drinkwater.databinding.FragmentWaterMainBinding
import com.johnzieman.ziemapp.drinkwater.interfaces.OnCheckRegistration
import com.johnzieman.ziemapp.drinkwater.interfaces.OnDrinksCountClicked
import com.johnzieman.ziemapp.drinkwater.interfaces.OnOtherDrinksItemClicked
import com.johnzieman.ziemapp.drinkwater.models.DailyStory
import com.johnzieman.ziemapp.drinkwater.models.WaterDaily
import com.johnzieman.ziemapp.drinkwater.ui.adapters.DrinkInMLAdapter
import com.johnzieman.ziemapp.drinkwater.ui.adapters.ExpandableAdapter
import com.johnzieman.ziemapp.drinkwater.ui.adapters.OtherDrinkAdapter
import com.johnzieman.ziemapp.drinkwater.ui.viewmodels.WaterMainViewModel
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "WATERMAIN"

class WaterMain : Fragment() {
    private lateinit var binding: FragmentWaterMainBinding
    private lateinit var adapter: OtherDrinkAdapter
    private lateinit var adapterInMl: DrinkInMLAdapter
    private lateinit var expandableAdapter: ExpandableAdapter
    private lateinit var waterDaily: WaterDaily
    private var onCheckRegistration: OnCheckRegistration? = null

    private var waterMlCount: Float = 0F

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

                    binding.pureWaterDrank.text = getString(
                        R.string.pure_water_drank,
                        String.format("%.1f", waterDaily[0].drunk),
                        String.format("%.1f", waterDaily[0].dailyRate)
                    )

                    binding.cupsDrank.text = getString(
                        R.string.cups_drank,
                        waterDaily[0].cupDrunk.toString(),
                        waterDaily[0].cupsRate.toString()
                    )
                    binding.otherDrinksDrank.text =
                        String.format("%.1f", waterDaily[0].otherDrinks) + "ml"

                    if (waterDaily[0].lastTimeDrankAnyWater == getString(R.string.no_info_yet) && waterDaily[0].lastTimeDrankAnyWaterReservation == getString(
                            R.string.no_info_yet
                        )
                    ) {
                        binding.lastTimeDrink.text = getString(R.string.no_info_yet)
                    } else {
                        if (waterDaily[0].lastTimeDrankAnyWater == getString(R.string.no_info_yet)) {
                            binding.lastTimeDrink.text =
                                getString(
                                    R.string.last_time_drank,
                                    waterDaily[0].lastTimeDrankAnyWaterReservation
                                )
                        } else {
                            binding.lastTimeDrink.text = getString(
                                R.string.last_time_drank,
                                waterDaily[0].lastTimeDrankAnyWater
                            )
                        }
                    }


                }
                waterMainViewModel.getUsers().observe(viewLifecycleOwner) {
                    binding.userName.text = "Hi, ${it[0].userName}"
                }
                waterMlCount = prefs.pull(ML)
                binding.drinkItemInMl.text = waterMlCount.toInt().toString() + "ml  "
            }
        }

        return binding.root
    }

    companion object {
        private const val ML = "ml"
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val displayMetrics = requireContext().resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density

        Log.d(TAG, dpHeight.toInt().toString() + "dp")

        binding.addWater.setOnClickListener {
            waterDaily.drunk += waterMlCount
            waterDaily.cupDrunk += 1
            val sdf = SimpleDateFormat("hh:mm")
            val currentDate = sdf.format(Date())
            waterDaily.lastTimeDrankAnyWaterReservation = waterDaily.lastTimeDrankAnyWater
            waterDaily.lastTimeDrankAnyWater = currentDate

            Log.d(TAG, currentDate)
            waterMainViewModel.updateDay(waterDaily = waterDaily)
            playWaterAnimation()
            showEnoughErrorMessage()
            if (waterDaily.drunk > waterDaily.dailyRate) {
                binding.waterMlCard.visibility = View.INVISIBLE
            } else {
                binding.waterMlCard.visibility = View.VISIBLE
            }

            waterMainViewModel.addOneDrinkHistory(
                DailyStory(
                    logo = R.drawable.water,
                    timeList = currentDate,
                    howMuchList = waterMlCount
                )
            )

        }

        binding.removeWater.setOnClickListener {
            if (waterDaily.cupDrunk > 0) {
                waterDaily.drunk -= waterMlCount
                waterDaily.cupDrunk -= 1

                if (waterDaily.cupDrunk == 0) {
                    waterDaily.lastTimeDrankAnyWaterReservation = getString(R.string.no_info_yet)
                }

                waterDaily.lastTimeDrankAnyWater = getString(R.string.no_info_yet)

                waterMainViewModel.updateDay(waterDaily = waterDaily)
            } else {
                Log.d(TAG, "Wrong expression!")
            }
            showEnoughErrorMessage()
            if (waterDaily.drunk > waterDaily.dailyRate) {
                binding.waterMlCard.visibility = View.INVISIBLE
            } else {
                binding.waterMlCard.visibility = View.VISIBLE
            }
        }

        expandableAdapter = ExpandableAdapter()
        binding.drinkHistoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext()).also {
                LinearLayoutManager.VERTICAL
            }
        waterMainViewModel.getHistoryList().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.expandableButton.visibility = View.GONE
            } else {
                Log.d(TAG, it[0].timeList.toString())
                binding.expandableButton.visibility = View.VISIBLE
                expandableAdapter.drinks = it
                binding.drinkHistoryRecyclerView.adapter = expandableAdapter
            }
        }


        adapter = OtherDrinkAdapter(object : OnOtherDrinksItemClicked {
            override fun onClick() {
                adapterInMl = DrinkInMLAdapter(
                    object : OnDrinksCountClicked {
                        override fun onClick(ml: Float) {
                            waterDaily.otherDrinks += ml
                            val sdf = SimpleDateFormat("hh:mm")
                            val currentDate = sdf.format(Date())
                            waterDaily.lastTimeDrankAnyWaterReservation =
                                waterDaily.lastTimeDrankAnyWater
                            waterDaily.lastTimeDrankAnyWater = currentDate
                            waterMainViewModel.updateDay(waterDaily)
                            showOtherDrinksList()
                        }
                    }
                )
                adapterInMl.portion = listOfMl()
                showOthherDrinksMlList()
                binding.otherDrinksTextViewPanel.setOnClickListener {
                    showOtherDrinksList()
                }

            }
        })
        adapter.drinks = listOf(
            R.drawable.one,
            R.drawable.three,
            R.drawable.eight,
            R.drawable.ten,
            R.drawable.tea,
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

        binding.waterMlCard.setOnClickListener {
            binding.waterMlCard.visibility = View.GONE
            binding.waterInMlRecyclerView.visibility = View.VISIBLE
            binding.waterInMlRecyclerView.layoutManager =
                LinearLayoutManager(requireContext()).apply {
                    this.orientation = LinearLayoutManager.VERTICAL
                }
            adapterInMl = DrinkInMLAdapter(object : OnDrinksCountClicked {
                override fun onClick(ml: Float) {
                    prefs.push(ML, ml)
                    binding.waterMlCard.visibility = View.VISIBLE
                    binding.waterInMlRecyclerView.visibility = View.GONE
                    binding.drinkItemInMl.text = prefs.pull<Float>(ML).toInt().toString() + "ml  "
                    waterMlCount = ml
                    val waterRemain = waterDaily.dailyRate - waterDaily.drunk
                    waterDaily.cupsRate = (waterRemain / waterMlCount).toInt().inc()
                    waterDaily.cupDrunk = 0
                    waterMainViewModel.updateDay(waterDaily)
                }
            })
            adapterInMl.portion = listOfMl()
            binding.waterInMlRecyclerView.adapter = adapterInMl
        }

        binding.expandableButton.setOnClickListener {
            if (binding.drinkHistoryRecyclerView.visibility == View.GONE) {
                binding.expandableButton.text = getString(R.string.collapse)
                TransitionManager.beginDelayedTransition(binding.card, AutoTransition())
                binding.waterMlCard.visibility = View.GONE
                binding.animationView.visibility = View.GONE
                binding.addWaterLinearLayout.visibility = View.GONE
                binding.otherDrinksListCard.visibility = View.GONE
                binding.userName.visibility = View.GONE
                binding.drinkHistoryRecyclerView.visibility = View.VISIBLE

            } else {
                binding.expandableButton.text = getString(R.string.expand)
                TransitionManager.beginDelayedTransition(binding.card, AutoTransition())
                binding.waterMlCard.visibility = View.VISIBLE
                binding.animationView.visibility = View.VISIBLE
                binding.addWaterLinearLayout.visibility = View.VISIBLE
                binding.otherDrinksListCard.visibility = View.VISIBLE
                binding.userName.visibility = View.VISIBLE
                binding.drinkHistoryRecyclerView.visibility = View.GONE
            }
        }

    }


    private fun showEnoughErrorMessage() {
        if (waterDaily.drunk > waterDaily.dailyRate) {
            binding.cupsDrank.setTextColor(resources.getColor(R.color.red))
            binding.pureWaterDrank.setTextColor(resources.getColor(R.color.red))
            binding.otherDrinksDrank.setTextColor(resources.getColor(R.color.red))
            Toast.makeText(
                requireContext(),
                "You have drunk too much, stop!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            binding.cupsDrank.setTextColor(resources.getColor(R.color.black))
            binding.pureWaterDrank.setTextColor(resources.getColor(R.color.black))
            binding.otherDrinksDrank.setTextColor(resources.getColor(R.color.black))
        }
    }


    private fun showOthherDrinksMlList() {
        binding.otherDrinksTextViewPanel.text = getString(R.string.choose_the_ml)
        binding.otherDrinksTextViewPanel.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_baseline_close_24,
            0
        )
        binding.recyclerView.adapter = adapterInMl
    }

    private fun showOtherDrinksList() {
        binding.otherDrinksTextViewPanel.text = getString(R.string.other_drinks)
        binding.otherDrinksTextViewPanel.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            0,
            0
        )
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


    private fun listOfMl(): List<String> = listOf(
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