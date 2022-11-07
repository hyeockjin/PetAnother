package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentAddDogBinding
import com.lx.project5.databinding.FragmentEditDogBinding
import com.lx.project5.databinding.FragmentFirstBinding

class EditDogFragment : Fragment() {
    var _binding: FragmentEditDogBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditDogBinding.inflate(inflater, container, false)

        return binding.root
    }

}