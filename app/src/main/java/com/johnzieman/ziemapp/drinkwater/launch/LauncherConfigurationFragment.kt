package com.johnzieman.ziemapp.drinkwater.launch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.databinding.FragmentLauncherConfigurationBinding
import com.johnzieman.ziemapp.drinkwater.launch.viewModels.LaunchConfigurationViewModel


class LauncherConfigurationFragment : Fragment() {
    private lateinit var binding: FragmentLauncherConfigurationBinding
    private val launchConfigurationViewModel: LaunchConfigurationViewModel by lazy {
        ViewModelProvider(this).get(LaunchConfigurationViewModel::class.java)
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
        val weightItems = launchConfigurationViewModel.weightMetrics
        val heightItems = launchConfigurationViewModel.heightMetrics
        val genderItems = launchConfigurationViewModel.genders
        val weightAdapter = ArrayAdapter(requireContext(), R.layout.weight_item_layout, weightItems)
        val heightAdapter = ArrayAdapter(requireContext(), R.layout.weight_item_layout, heightItems)
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.weight_item_layout, genderItems)
        binding.userWeightMetrics.setAdapter(weightAdapter)
        binding.userHeightMetric.setAdapter(heightAdapter)
        binding.userGender.setAdapter(genderAdapter)
        return binding.root
    }

}