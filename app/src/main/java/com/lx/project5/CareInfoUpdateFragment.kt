package com.lx.project5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lx.api.BasicClient
import com.lx.data.CareListResponse
import com.lx.project5.AppData.Companion.loginData
import com.lx.project5.databinding.FragmentCareInfoUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CareInfoUpdateFragment : Fragment() {
    var _binding: FragmentCareInfoUpdateBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, asavedInstanceState: Bundle?): View? {
        _binding = FragmentCareInfoUpdateBinding.inflate(inflater, container, false)

        binding.showCareId.text = loginData?.careId

        binding.infoUpdateButton.setOnClickListener {
            updateCare()

        }

        return binding.root
    }

    //회원 정보 수정 요청하기
    fun updateCare() {
        val careNo = loginData?.careNo
        val carePw1 = binding.checkPw.text.toString()
        val carePw2 = loginData?.carePw
        val careImage = "1"
        val careAddress = binding.updateAddress.text.toString()
        val careName = binding.updateName.text.toString()
        (activity as MainActivity).showToast(careNo!!)
        if (carePw1.equals(carePw2)){
            BasicClient.api.postCareUpdate(
                requestCode = "1001",
                careNo = careNo,
                careName = careName,
                careAddress = careAddress,
                careImage = careImage
            ).enqueue(object : Callback<CareListResponse> {
                override fun onResponse(call: Call<CareListResponse>, response: Response<CareListResponse>) {
                    (activity as MainActivity).showToast("수정되었습니다.")
                    loginData?.careName = careName
                    loginData?.careAddress = careAddress
                }
                override fun onFailure(call: Call<CareListResponse>, t: Throwable) {
                    (activity as MainActivity).showToast("2")
                    (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
                }

            })
        }else {
            (activity as MainActivity).showToast("비밀번호가 틀립니다.")

        }

    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}