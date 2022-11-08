package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentJoin1Binding
import com.lx.project5.databinding.FragmentLoginBinding

class Join1Fragment : Fragment() {
    var _binding: FragmentJoin1Binding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJoin1Binding.inflate(inflater, container, false)

        binding.nextButton1.setOnClickListener {

        }

        return binding.root
    }

}