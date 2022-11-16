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
        if(AppData.goIndex == 2){
            val assignTime = "${AppData.choiceRegisterItem?.startTime} ~ ${AppData.choiceRegisterItem?.endTime}"
            binding.outputTime.text = assignTime


            // 사람 정보 부터
            BasicClient.api.getMemberInfo(
                requestCode = "1001",
                memberNo = AppData.choiceRegisterItem?.memberNo.toString()

            ).enqueue(object : Callback<MemberListResponse> {
                override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {

                    AppData.memberData?.memberAddress = response.body()?.data?.get(0)?.memberAddress
                    AppData.memberData?.memberImage = response.body()?.data?.get(0)?.memberImage
                    AppData.memberData?.memberName = response.body()?.data?.get(0)?.memberName
                    AppData.memberData?.memberNo = response.body()?.data?.get(0)?.memberNo
                    binding.nameOutput.text = AppData.memberData?.memberName

                    (activity as MainActivity).showToast("1")
                }
                override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {

                    (activity as MainActivity).showToast("2")
                }

            })
            // 그다음 개정보
            BasicClient.api.getDogInfo(
                requestCode = "1001",
                dogNo = AppData.choiceRegisterItem?.dogNo.toString()

            ).enqueue(object : Callback<DogListResponse> {
                override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {

                    AppData.dogData?.dogNo = response.body()?.data?.get(0)?.dogNo.toString()
                    AppData.dogData?.dogAge = response.body()?.data?.get(0)?.dogAge.toString()
                    AppData.dogData?.dogImage = response.body()?.data?.get(0)?.dogImage.toString()
                    AppData.dogData?.dogBreed = response.body()?.data?.get(0)?.dogBreed.toString()
                    AppData.dogData?.dogCharacter = response.body()?.data?.get(0)?.dogCharacter.toString()
                    AppData.dogData?.dogEducation = response.body()?.data?.get(0)?.dogEducation.toString()
                    AppData.dogData?.dogGender = response.body()?.data?.get(0)?.dogGender.toString()
                    binding.outputDogName.text = AppData.dogData?.dogName

                    (activity as MainActivity).showToast("1")
                }
                override fun onFailure(call: Call<DogListResponse>, t: Throwable) {

                    (activity as MainActivity).showToast("2")
                }

            })


        }else if (AppData.goIndex == 1){
            val assignTime = "${AppData.writeRegisterItem?.startTime} ~ ${AppData.writeRegisterItem?.endTime}"
            binding.outputTime.text = assignTime

            // 사람 정보 부터
            BasicClient.api.getMemberInfo(
                requestCode = "1001",
                memberNo = AppData.writeRegisterItem?.memberNo.toString()

            ).enqueue(object : Callback<MemberListResponse> {
                override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {

                    AppData.memberData?.memberAddress = response.body()?.data?.get(0)?.memberAddress
                    AppData.memberData?.memberImage = response.body()?.data?.get(0)?.memberImage
                    AppData.memberData?.memberName = response.body()?.data?.get(0)?.memberName
                    AppData.memberData?.memberNo = response.body()?.data?.get(0)?.memberNo
                    binding.nameOutput.text = AppData.memberData?.memberName

                    (activity as MainActivity).showToast("1")
                }
                override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {

                    (activity as MainActivity).showToast("2")
                }

            })
            // 그다음 개정보
            BasicClient.api.getDogInfo(
                requestCode = "1001",
                dogNo = AppData.writeRegisterItem?.dogNo.toString()

            ).enqueue(object : Callback<DogListResponse> {
                override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {

                    AppData.dogData?.dogNo = response.body()?.data?.get(0)?.dogNo.toString()
                    AppData.dogData?.dogAge = response.body()?.data?.get(0)?.dogAge.toString()
                    AppData.dogData?.dogImage = response.body()?.data?.get(0)?.dogImage.toString()
                    AppData.dogData?.dogBreed = response.body()?.data?.get(0)?.dogBreed.toString()
                    AppData.dogData?.dogCharacter = response.body()?.data?.get(0)?.dogCharacter.toString()
                    AppData.dogData?.dogEducation = response.body()?.data?.get(0)?.dogEducation.toString()
                    AppData.dogData?.dogGender = response.body()?.data?.get(0)?.dogGender.toString()
                    binding.outputDogName.text = AppData.dogData?.dogName

                    (activity as MainActivity).showToast("1")
                }
                override fun onFailure(call: Call<DogListResponse>, t: Throwable) {

                    (activity as MainActivity).showToast("2")
                }

            })
        }




    }

}