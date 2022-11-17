package com.lx.project5

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lx.project5.databinding.FragmentCareInfoBinding
import com.lx.project5.databinding.FragmentMemberInfoBinding

class MemberInfoFragment : Fragment() {
    var _binding: FragmentMemberInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initView()

        binding.backButton4.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcareMain)
        }
        return binding.root
    }

    fun initView(){
        AppData.memberData?.apply{
            this.memberImage .let{
                val uri = Uri.parse("http://192.168.0.15:8001${memberImage}")
                Glide.with(binding.memImageView).load(uri).into(binding.memImageView)
            }
            binding.memName.text = "${AppData.memberData?.memberName}"
            binding.memAddress.text = "${AppData.memberData?.memberAddress}"

        }
    }


}