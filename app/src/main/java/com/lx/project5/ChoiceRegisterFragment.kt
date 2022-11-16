package com.lx.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.api.BasicClient
import com.lx.data.ChoiceRegisterResponse
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

        // 4. 아이템을 클릭했을 때 동작할 코드 넣어주기
        choiceRegisterAdapter?.listener = object: OnChoiceRegisterItemClickListener {
            override fun onItemClick(holder: ChoiceRegisterAdapter.ViewHolder?, view: View?, position: Int) {
                choiceRegisterAdapter?.apply {
                    val item = items.get(position)

                    AppData.choiceRegisterItem = item
                    push

                    AppData.goIndex = 2

//                    val petInfoIntent = Intent(context, PetInfoFragment::class.java)
//                    petInfoLauncher.launch(petInfoIntent)
                    (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcareInfo)

                }
            }

        }

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