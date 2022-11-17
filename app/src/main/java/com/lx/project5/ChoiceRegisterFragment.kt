package com.lx.project5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.api.BasicClient
import com.lx.data.ChoiceRegisterResponse
import com.lx.data.DogListResponse
import com.lx.data.MemberListResponse
import com.lx.project5.databinding.FragmentChoiceRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChoiceRegisterFragment : Fragment() {
    var _binding: FragmentChoiceRegisterBinding? = null
    val binding get() = _binding!!

    var choiceRegisterAdapter: ChoiceRegisterAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChoiceRegisterBinding.inflate(inflater, container, false)

        initList()
        petView()

        return binding.root
    }

    // 리스트 초기화
    fun initList() {

        // 1. 리스트의 모양을 담당하는 것
        // (LinearLayoutManager : 아래쪽으로 아이템들이 보이는 것, GridLayoutManager : 격자 형태로 보이는 것)
        val layoutManager = LinearLayoutManager(context)
        binding.crrv.layoutManager = layoutManager

        // 2. 어댑터를 설정하는 것
        // 실제 데이터를 관리하고 각 아이템의 모양을 만들어주는 것
        choiceRegisterAdapter = ChoiceRegisterAdapter()
        binding.crrv.adapter = choiceRegisterAdapter
        //?


        // 4. 아이템을 클릭했을 때 동작할 코드 넣어주기
        choiceRegisterAdapter?.listener = object: OnChoiceRegisterItemClickListener {
            override fun onItemClick(holder: ChoiceRegisterAdapter.ViewHolder?, view: View?, position: Int) {
                choiceRegisterAdapter?.apply {
                    val item = items.get(position)

                    AppData.choiceRegisterItem = item

                    Log.v("멍청이11", "${AppData.choiceRegisterItem?.acrn}")
                    Log.v("멍청이11", "${AppData.choiceRegisterItem?.memberNo}")
                    AppData.goIndex = 2
                    pushChoiceRegisterData()





//                    val petInfoIntent = Intent(context, PetInfoFragment::class.java)
//                    petInfoLauncher.launch(petInfoIntent)


                }
            }

        }

    }
    fun pushChoiceRegisterData(){
        // 사람 정보 부터
        BasicClient.api.getMemberInfo(
            requestCode = "1001",
            memberNo = AppData.choiceRegisterItem?.memberNo.toString()

        ).enqueue(object : Callback<MemberListResponse> {
            override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {

                AppData.memberData = MemberData()
                AppData.memberData?.memberNo = response.body()?.data?.get(0)?.memberNo
                AppData.memberData?.memberAddress = response.body()?.data?.get(0)?.memberAddress
                AppData.memberData?.memberImage = response.body()?.data?.get(0)?.memberImage
                AppData.memberData?.memberName = response.body()?.data?.get(0)?.memberName
                AppData.memberData?.memberNo = response.body()?.data?.get(0)?.memberNo
                Log.v("멍청이0", "${response.body()?.data?.get(0)?.memberNo}")
                Log.v("멍청이0", "${AppData.memberData?.memberNo}")

            }
            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {

            }

        })
        // 그다음 개정보
        BasicClient.api.getDogInfo(
            requestCode = "1001",
            dogNo = AppData.choiceRegisterItem?.dogNo.toString()

        ).enqueue(object : Callback<DogListResponse> {
            override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {
                AppData.dogData = PetData()
                AppData.dogData?.dogNo = response.body()?.data?.get(0)?.dogNo.toString()
                AppData.dogData?.dogAge = response.body()?.data?.get(0)?.dogAge.toString()
                AppData.dogData?.dogImage = response.body()?.data?.get(0)?.dogImage.toString()
                AppData.dogData?.dogBreed = response.body()?.data?.get(0)?.dogBreed.toString()
                AppData.dogData?.dogCharacter = response.body()?.data?.get(0)?.dogCharacter.toString()
                AppData.dogData?.dogEducation = response.body()?.data?.get(0)?.dogEducation.toString()
                AppData.dogData?.dogGender = response.body()?.data?.get(0)?.dogGender.toString()

                Log.v("멍청이1", "${AppData.dogData?.dogNo}")
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcareInfo)

            }
            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {

            }
        })
    }


    fun petView() {

        var careNo = AppData.loginData?.careNo
        BasicClient.api.getChoiceRegisterList(
            requestCode = "1001",
            careNo = careNo.toString()
        ).enqueue(object: Callback<ChoiceRegisterResponse> {
            override fun onResponse(call: Call<ChoiceRegisterResponse>, response: Response<ChoiceRegisterResponse>) {

                addPetList(response)
            }

            override fun onFailure(call: Call<ChoiceRegisterResponse>, t: Throwable) {
            }

        })

    }

    fun addPetList(response: Response<ChoiceRegisterResponse>){

        choiceRegisterAdapter?.apply{
            response.body()?.data?.let {
                for(item in it) {
                    this.items.add(ChoiceRegisterData(
                        item.acrn,
                        item.memberNo,
                        item.careNo,
                        item.dogNo,
                        item.startTime,
                        item.endTime,
                        item.assignTitle,
                        item.assignContent,
                        item.memberName
                    )
                    )
                }
            }
            this.notifyDataSetChanged()

        }

    }

}