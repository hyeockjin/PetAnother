package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentJoin1Binding
import com.lx.project5.databinding.FragmentJoin2Binding

class Join2Fragment : Fragment() {
    var _binding: FragmentJoin2Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJoin2Binding.inflate(inflater, container, false)

        return binding.root
    }

}