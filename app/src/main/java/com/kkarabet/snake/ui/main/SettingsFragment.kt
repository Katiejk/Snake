package com.kkarabet.snake.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioGroup
import androidx.navigation.findNavController
import com.kkarabet.snake.R

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var colorSelector: RadioGroup
    private lateinit var foodSelector:RadioGroup
    private lateinit var applyButton:ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.settings_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        colorSelector = view.findViewById(R.id.radioGroup2)
        foodSelector = view.findViewById(R.id.radioGroup)
        applyButton = view.findViewById(R.id.imageButton3)

        when (viewModel.color){
            0 -> colorSelector.check(R.id.red)
            1 -> colorSelector.check(R.id.orange)
            2 -> colorSelector.check(R.id.yellow)
            3 -> colorSelector.check(R.id.ltgreen)
            4 -> colorSelector.check(R.id.dkgreen)
            5 -> colorSelector.check(R.id.ltblue)
            6 -> colorSelector.check(R.id.dkblue)
            7 -> colorSelector.check(R.id.purple)
            8 -> colorSelector.check(R.id.black)
        }

        when (viewModel.food){
            0 -> foodSelector.check(R.id.realistic)
            1 -> foodSelector.check(R.id.junk)
            2 -> foodSelector.check(R.id.health)
        }

        applyButton.setOnClickListener {
            var newColor = 0
            var newFood = 0
            when (colorSelector.checkedRadioButtonId){
                R.id.red -> newColor = 0
                R.id.orange -> newColor = 1
                R.id.yellow -> newColor = 2
                R.id.ltgreen -> newColor = 3
                R.id.dkgreen -> newColor = 4
                R.id.ltblue -> newColor = 5
                R.id.dkblue -> newColor = 6
                R.id.purple -> newColor = 7
                R.id.black -> newColor = 8
            }
            when (foodSelector.checkedRadioButtonId) {
                R.id.realistic -> newFood = 0
                R.id.junk -> newFood = 1
                R.id.health -> newFood = 2
            }
            viewModel.updateColor(newColor)
            viewModel.updateFood(newFood)
            view.findNavController().navigate(R.id.action_settingsFragment_to_mainFragment)
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
    }
}