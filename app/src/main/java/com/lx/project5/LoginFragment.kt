package com.lx.project5

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lx.api.BasicClient
import com.lx.data.CareListResponse
import com.lx.project5.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.register.setOnClickListener {
            val curActivity = activity as MainActivity
            curActivity.onFragmentChanged(MainActivity.ScreenItem.ITEMjoin1)
        }

        binding.loginButton.setOnClickListener {
            readCare()
        }

        return binding.root
    }

    fun readCare() {
        val careId = binding.loginId.text.toString()
        val carePw = binding.loginPassword.text.toString()

        BasicClient.api.postCareLogin(
            requestCode = "1001",
            careId = careId,
            carePw = carePw
        ).enqueue(object : Callback<CareListResponse> {
            override fun onResponse(call: Call<CareListResponse>, response: Response<CareListResponse>) {
                val checkCare = response.body()?.header?.total.toString()
                println(checkCare)

                if(checkCare == "1"){
                    AppData.userdata="${response.body()?.data.toString()}"
                    (activity as MainActivity).showToast("로그인 성공")
                    AppData.loginData = LoginData()
                    AppData.loginData?.careId = careId
                    AppData.loginData?.carePw = carePw
                    AppData.loginData?.careName = response.body()?.data?.get(0)?.careName.toString()
                    AppData.loginData?.careNo = response.body()?.data?.get(0)?.careNo.toString()
                    AppData.loginData?.careX = response.body()?.data?.get(0)?.careX.toString()
                    AppData.loginData?.careY = response.body()?.data?.get(0)?.careY.toString()
                    AppData.loginData?.careExperience = response.body()?.data?.get(0)?.careExperience.toString()
                    AppData.loginData?.careApproval = response.body()?.data?.get(0)?.careApproval.toString()
                    AppData.loginData?.careEducation = response.body()?.data?.get(0)?.careEducation.toString()
                    AppData.loginData?.careAddress = response.body()?.data?.get(0)?.careAddress.toString()
                    AppData.loginData?.careImage = response.body()?.data?.get(0)?.careImage.toString()
                    (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
                } else if(checkCare == "0"){
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("로그인")
                    builder.setMessage("아이디/비밀번호를 다시 입력해주세요.")
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        toast("Positive")
                    }
                    builder.show()
                    binding.loginId.setText("")
                    binding.loginPassword.setText("")
                }


            }
            override fun onFailure(call: Call<CareListResponse>, t: Throwable) {
                (activity as MainActivity).showToast("qkqh")
            }

        })

    }

    fun toast(message:String){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show()
    }

    }



