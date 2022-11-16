package com.lx.project5

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.lx.api.BasicClient
import com.lx.data.CareListResponse
import com.lx.data.MemberListResponse
import com.lx.project5.databinding.FragmentFirstBinding
import com.lx.project5.databinding.FragmentMyPageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : Fragment() {
    var _binding: FragmentMyPageBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        initView()

        binding.button2.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMupdate)
        }

        //교육수강신청
        binding.button3.setOnClickListener{
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMvideo)
        }


        return binding.root
    }

    fun initView(){
        AppData.loginData.apply{
            this?.careImage?.let{
                val uri = Uri.parse("http://192.168.0.215:8001${careImage}")
                Glide.with(binding.imageView12).load(uri).into(binding.imageView12)
            }
            binding.textView7.text = AppData.loginData?.careName
            binding.textView8.text = AppData.loginData?.careId
            binding.textView9.text = AppData.loginData?.careAddress
        }
    }


}