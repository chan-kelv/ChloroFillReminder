package com.kelvinfocus.chlorofillreminder.ui.addplant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kelvinfocus.chlorofillreminder.R
import com.kelvinfocus.chlorofillreminder.databinding.FragmentAddPlantBinding
import com.kelvinfocus.chlorofillreminder.model.TimeFrequencyUnit
import com.kelvinfocus.chlorofillreminder.model.TimeIntervals
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPlantFragment : Fragment() {
    private lateinit var binding: FragmentAddPlantBinding
    private lateinit var addPlantVM: AddPlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlantBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        addPlantVM = ViewModelProvider(this).get(AddPlantViewModel::class.java)

        setupViewData()
        setupHandlers()
        return view
    }

    private fun setupViewData() {
        setTimeFrequencyUnitSpinners()
        setTimeIntervalSpinners()
    }

    private fun setTimeIntervalSpinners() {
        binding.waterFrequencyAlarm.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                TimeIntervals.generateValidTimeIntervals()
            )
        )
        binding.waterFrequencyAlarm.setHint(R.string.no_alarm)

        binding.fertilizerFrequencyAlarm.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                TimeIntervals.generateValidTimeIntervals()
            )
        )
        binding.fertilizerFrequencyAlarm.setHint(R.string.no_alarm)
    }

    private fun setTimeFrequencyUnitSpinners() {
        val timeIntervals = TimeFrequencyUnit.getAsList()
        binding.waterFrequencyUnit.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                timeIntervals
            )
        )
        binding.waterFrequencyUnit.setText(timeIntervals[0])

        binding.fertilizerFrequencyUnit.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                timeIntervals
            )
        )
        binding.fertilizerFrequencyUnit.setHint(R.string.no_time_unit)
    }

    private fun setupHandlers() {
        binding.saveNewPlantButton.setOnClickListener {
            addPlantVM.newPlantValidCheck(
                binding.waterAmountOfTimeInput.text.toString()
            )
        }
        addPlantVM.waterScheduleInvalid.observe(viewLifecycleOwner) { invalidWaterFreq ->
            if (invalidWaterFreq) {
                binding.waterAmountOfTimeInput.error = "Invalid number"
            }
        }

        addPlantVM.newPlantValid.observe(viewLifecycleOwner) { isValidPlant ->
            if (isValidPlant) {
                addPlantVM.savePlant(
                    binding.plantPhoto,
                    binding.plantName.text.toString(),
                    binding.waterAmountOfTimeInput.text.toString(),
                    binding.waterFrequencyUnit.text.toString(),
                    binding.waterFrequencyAlarm.text.toString(),
                    binding.fertilizeAmountOfTimesInput.text.toString(),
                    binding.fertilizerFrequencyUnit.text.toString(),
                    binding.fertilizerFrequencyAlarm.text.toString(),
                    binding.plantNotes.text.toString(),
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): AddPlantFragment {
            return AddPlantFragment()
        }
    }
}