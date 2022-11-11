package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.*

class CompleteFragment : Fragment() {
    var _binding: FragmentCompleteBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)

        binding.reviewButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMassess)

        }

        binding.completeButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMend)

        }


        return binding.root
    }

}