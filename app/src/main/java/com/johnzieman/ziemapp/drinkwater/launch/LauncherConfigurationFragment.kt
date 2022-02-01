package com.johnzieman.ziemapp.drinkwater.launch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.models.User
import com.johnzieman.ziemapp.drinkwater.databinding.FragmentLauncherConfigurationBinding
import com.johnzieman.ziemapp.drinkwater.interfaces.OnLauncherOpener
import com.johnzieman.ziemapp.drinkwater.launch.viewModels.LaunchConfigurationViewModel

private const val TAG = "LAUNCHERCONFIG"
class LauncherConfigurationFragment : Fragment() {

    private var onLauncherOpener: OnLauncherOpener? = null


    private lateinit var binding: FragmentLauncherConfigurationBinding
    private val launchConfigurationViewModel: LaunchConfigurationViewModel by lazy {
        ViewModelProvider(this).get(LaunchConfigurationViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onLauncherOpener = context as OnLauncherOpener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_launcher_configuration,
            container,
            false
        )
        //spinner adapters
        val weightItems = launchConfigurationViewModel.weightMetrics
        val heightItems = launchConfigurationViewModel.heightMetrics
        val genderItems = launchConfigurationViewModel.genders
        val weightAdapter = ArrayAdapter(requireContext(), R.layout.weight_item_layout, weightItems)
        val heightAdapter = ArrayAdapter(requireContext(), R.layout.weight_item_layout, heightItems)
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.weight_item_layout, genderItems)
        binding.userWeightMetrics.setAdapter(weightAdapter)
        binding.userHeightMetric.setAdapter(heightAdapter)
        binding.userGender.setAdapter(genderAdapter)


        //getting data and saving to room database
        binding.button3.setOnClickListener {
            val userName = binding.userName.text.toString()
            val userAge = binding.userAge.text.toString()
            val userHeight = binding.userHeight.text.toString()
            val userWeight = binding.userWeight.text.toString()
            val userGender = binding.userGender.text.toString()
            val weightMetrics = binding.userWeightMetrics.text.toString()
            val heightMetrics = binding.userHeightMetric.text.toString()

            if (heightMetrics.isNotEmpty()){
                binding.userHeightMetric.error = null
            }
            if (weightMetrics.isNotEmpty()){
                binding.userWeightMetrics.error = null
            }

            if (userName.isNotEmpty()) {
                if (userAge.isNotEmpty()) {
                    if (userHeight.isNotEmpty()) {
                        if (userWeight.isNotEmpty()) {
                            if (userGender.isNotEmpty()) {
                                if (weightMetrics.isNotEmpty()) {
                                    binding.userWeightMetrics.error = null
                                    if (heightMetrics.isNotEmpty()) {
                                        binding.userHeightMetric.error = null
                                        val user = User(
                                            isConfigured = 1,
                                            userName = userName,
                                            userAge = userAge,
                                            userHeight = userHeight,
                                            userWeight = userWeight,
                                            userSex = userGender,
                                            weightMetrics = weightMetrics,
                                            heightMetrics = heightMetrics
                                        )
                                        launchConfigurationViewModel.addUser(user)
                                        Log.d(TAG, "User saved")
                                        onLauncherOpener?.onOpenUSerResultFragment()

                                    } else {
                                        binding.userHeightMetric.error = "Select the unit"
                                    }
                                } else {
                                    binding.userWeightMetrics.error = "Select the unit"
                                }
                            } else {
                                binding.userGender.error = "Enter your gender"
                            }
                        } else {
                            binding.userWeight.error = "Enter your weight"
                        }
                    } else {
                        binding.userHeight.error = "Enter your height"
                    }
                } else {
                    binding.userAge.error = "Enter your age"
                }
            } else {
                binding.userName.error = "Enter your name"
            }
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        onLauncherOpener = null
    }

}