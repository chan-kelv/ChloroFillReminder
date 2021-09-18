package com.kelvinfocus.chlorofillreminder.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kelvinfocus.chlorofillreminder.databinding.FragmentLandingBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LandingFragment:Fragment() {
    private lateinit var landingBinding: FragmentLandingBinding
    private lateinit var landingVM: LandingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        landingBinding = FragmentLandingBinding.inflate(inflater, container, false)
        val view = landingBinding.root

        // create view model
        landingVM = ViewModelProvider(this).get(LandingViewModel::class.java)

        setupHandlers()

        return view
    }

    private fun setupHandlers() {
        // hook up vm handlers
        landingVM.plantSchedule.observe(viewLifecycleOwner) {
            // load recycler view
        }

        // setup button handlers
        landingBinding.settingsButton.setOnClickListener {
            Timber.d("navigate to settings")
        }

        landingBinding.addPlantButton.setOnClickListener {
            Timber.d("navigate to add new plant")
        }

        landingBinding.reminderRecycler.setOnClickListener {
            Timber.d("navigate to reminder schedule")
        }

        landingBinding.seeAllPlantsButton.setOnClickListener {
            Timber.d("navigate to see all plants")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): LandingFragment {
            return LandingFragment()
        }
    }
}