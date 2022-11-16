package com.lx.project5

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lx.api.BasicApi
import com.lx.api.BasicClient
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

        // 사람 정보 부터
        BasicClient.api.getMemberList(
            requestCode = "1001",
            memberNo = AppData.choiceRegisterItem?.memberNo.toString()

        ).enqueue(object : Callback<MemberListResponse> {
            override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {


                (activity as MainActivity).showToast("1")

            }
            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {

                (activity as MainActivity).showToast("2")
            }

        })


        val assignTime = "${AppData.choiceRegisterItem?.startTime} ~ ${AppData.choiceRegisterItem?.endTime}"

        binding.nameOutput.text = AppData.choiceRegisterItem?.memberName
        binding.outputTime.text = assignTime

        val acrn =  AppData.choiceRegisterItem?.acrn



    }

}