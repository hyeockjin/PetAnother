package com.lx.project5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        savedInstanceState?.apply {
            initView(savedInstanceState)
        }



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

    fun initView(bundle: Bundle){

        if(AppData.goIndex == 2){
            AppData.memberData.apply{
                this?.memberImage?.let{
                    val uri = Uri.parse("http://192.168.0.15:8001${memberImage}")
                    Glide.with(binding.profileView).load(uri).into(binding.profileView)
                }
                val assignTime = "${AppData.choiceRegisterItem?.startTime} ~ ${AppData.choiceRegisterItem?.endTime}"
                binding.outputTime.text = assignTime
                binding.nameOutput.text = AppData.memberData?.memberName
                binding.outputDogName.text = AppData.dogData?.dogName

            }



        }else if (AppData.goIndex == 1){
            AppData.memberData.apply {
                this?.memberImage?.let {
                    val uri = Uri.parse("http://192.168.0.15:8001${memberImage}")
                    Glide.with(binding.profileView).load(uri).into(binding.profileView)
                }

                //binding.outputTime.text = bundle.getString("","0")
                binding.nameOutput.text = bundle.getString("memberName", "0")
                binding.outputDogName.text = bundle.getString("dogName", "0")
            }

            WriteRegisterData.apply {
                binding.outputTime.text = "${bundle.getString("startTime", "0")}~${bundle.getString("endTime","0")}"
                binding.textView10.text = bundle.getString("assignTitle", "0")
                binding.output2.text = bundle.getString("assignContent", "0")

            }

                //binding.nameOutput.text = AppData.memberData?.memberName
                //binding.outputDogName.text = AppData.dogData?.dogName //
                Log.v("멍","${WriteRegisterData.startTime}~${WriteRegisterData.endTime}")
                Log.v("야","${WriteRegisterData.assignTitle}")
                Log.v("야","${WriteRegisterData.assignContent}")
            }



            }



    }

