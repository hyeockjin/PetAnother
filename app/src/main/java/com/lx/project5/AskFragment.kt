package com.lx.project5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.project5.databinding.FragmentAskBinding
import com.lx.project5.databinding.FragmentEndBinding


class AskFragment : Fragment() {
    var _binding: FragmentAskBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAskBinding.inflate(inflater, container, false)

        return binding.root
    }

}