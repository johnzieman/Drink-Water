package com.johnzieman.ziemapp.drinkwater.launch

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.johnzieman.ziemapp.drinkwater.R
import com.johnzieman.ziemapp.drinkwater.interfaces.OnLauncherOpener


class LauncherFragment : Fragment() {
    private var onLauncherOpener: OnLauncherOpener? = null
    private lateinit var buttonNext: Button
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onLauncherOpener = context as OnLauncherOpener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_launcher, container, false)
        buttonNext = view.findViewById(R.id.button2)
        buttonNext.setOnClickListener {
            onLauncherOpener?.OnOpenUserConfig()
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        onLauncherOpener = null
    }

}