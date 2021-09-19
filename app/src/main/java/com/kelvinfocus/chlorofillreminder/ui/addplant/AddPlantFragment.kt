package com.kelvinfocus.chlorofillreminder.ui.addplant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kelvinfocus.chlorofillreminder.databinding.FragmentAddPlantBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlantFragment : Fragment() {
    private lateinit var addPlantBinding: FragmentAddPlantBinding
    private lateinit var addPlantVM: AddPlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addPlantBinding = FragmentAddPlantBinding.inflate(layoutInflater, container, false)
        val view = addPlantBinding.root

        addPlantVM = ViewModelProvider(this).get(AddPlantViewModel::class.java)

        setupView()
        setupHandlers()
        return view
    }

    private fun setupView() {
        addPlantBinding.waterFrequencyDuration.setAdapter(ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listOf("DAY(S)", "MONTH(S)")
        ))
        addPlantBinding.waterFrequencyDuration.setText("DAY(S)")
//        addPlantBinding.waterFrequencyDuration.setSelection(1)
        addPlantBinding.waterFrequencyAlarm.setText("10:00 AM")
        addPlantBinding.fertilizerFrequencyAlarm.setText("10:00 PM")
//        addPlantBinding.fertilizerFrequencyDuration.adapter =  ArrayAdapter<String>(
//            requireContext(),
//            android.R.layout.simple_dropdown_item_1line,
//            listOf("DAY(S)", "MONTH(S)")
//        )
//        addPlantBinding.fertilizerFrequencyDuration.setSelection(0)
//
//        addPlantBinding.waterFrequencyAlarm.adapter = ArrayAdapter<String>(
//            requireContext(),
//            android.R.layout.simple_dropdown_item_1line,
//            listOf("1:00 PM", "1:30 PM", "2 PM", "3 PM")
//        )
//        addPlantBinding.waterFrequencyAlarm.setSelection(3)
//
//        val adapter = ArrayAdapter<String>(
//            requireContext(),
//            android.R.layout.simple_dropdown_item_1line,
//            listOf("1:00 PM", "1:30 PM", "2 PM", "3 PM")
//        )
//        addPlantBinding.fertilizerFrequencyAlarm.adapter = adapter
//        addPlantBinding.fertilizerFrequencyAlarm.setSelection(1)
    }

    private fun setupHandlers() {

    }

    companion object {
        @JvmStatic
        fun newInstance(): AddPlantFragment {
            return AddPlantFragment()
        }
    }
}