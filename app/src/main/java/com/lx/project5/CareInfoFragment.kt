package com.lx.project5

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lx.api.BasicApi
import com.lx.api.BasicClient
import com.lx.data.DogListResponse
import com.lx.data.MemberListResponse
import com.lx.project5.databinding.FragmentCareInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareInfoFragment : Fragment() {
    var _binding: FragmentCareInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCareInfoBinding.inflate(inflater, container, false)
        initView()


        binding.button5.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcareTodolist)

        }

        binding.button6.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmemInfo)

        }

        binding.button8.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMdogInfo)

        }

        binding.imageButton3.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEM1)

        }



        return binding.root
    }
    fun initView(){
        Log.v("멍청이2", "사람 ${AppData.memberData?.memberNo}, 개 ${AppData.dogData?.dogNo}")
        if(AppData.goIndex == 2){
            val assignTime = "${AppData.choiceRegisterItem?.startTime} ~ ${AppData.choiceRegisterItem?.endTime}"
            binding.outputTime.text = assignTime
            binding.nameOutput.text = AppData.memberData?.memberName
            binding.outputDogName.text = AppData.dogData?.dogName


        }else if (AppData.goIndex == 1){
            Log.v("멍청이2", "사람 ${AppData.memberData}, 개 ${AppData.dogData}")
            binding.outputTime.text = "${AppData.writeRegisterItem?.startTime} ~ ${AppData.writeRegisterItem?.endTime}"
            binding.nameOutput.text = AppData.memberData?.memberName
            binding.outputDogName.text = AppData.dogData?.dogName

        }




    }

}