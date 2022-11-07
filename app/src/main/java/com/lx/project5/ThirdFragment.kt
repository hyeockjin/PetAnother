package com.lx.project5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {
    var _binding: FragmentThirdBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root
    }

}