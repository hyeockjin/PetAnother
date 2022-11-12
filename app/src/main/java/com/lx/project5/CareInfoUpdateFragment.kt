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

        binding.infoUpdateButton.setOnClickListener {
            updateCare()

        }

        return binding.root
    }

    //회원 정보 수정 요청하기
    fun updateCare() {
        val careId = binding.inputId.text.toString()
        val carePw = binding.inputPw.text.toString()
        val careImage = binding.inputImageView.textAlignment.toString()
        val careName = binding.inputName.text.toString()

        BasicClient.api.postCareUpdate(
            requestCode = "1001",
            careId = careId,
            carePw = carePw,
            careName = careName,
            careImage = careImage
        ).enqueue(object : Callback<CareListResponse> {
            override fun onResponse(call: Call<CareListResponse>, response: Response<CareListResponse>) {

            }
            override fun onFailure(call: Call<CareListResponse>, t: Throwable) {
                showToast("수정 성공")
                loginData?.careId = ""
                loginData?.careName = ""
                loginData?.careImage = ""
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
            }

        })

    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}