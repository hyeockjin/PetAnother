package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    var _binding: FragmentMyPageBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)

        binding.goDogList.setOnClickListener {

        }
        //교육수강신청
        binding.button3.setOnClickListener{
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMvideo)
        }


        return binding.root
    }

}