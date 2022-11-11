package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentCareMainBinding


class CareMainFragment : Fragment() {
    var _binding: FragmentCareMainBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCareMainBinding.inflate(inflater, container, false)

        binding.todoButton.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcareTodolist)

        }

        binding.Button5.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMaddDog)

        }

        binding.completeButton1.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcomplete)

        }

        return binding.root
    }

}