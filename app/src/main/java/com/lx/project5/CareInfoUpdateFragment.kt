package com.lx.project5

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.lx.api.BasicClient
import com.lx.data.CareListResponse
import com.lx.data.MemberListResponse
import com.lx.project5.databinding.FragmentCareInfoUpdateBinding
import com.permissionx.guolindev.PermissionX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CareInfoUpdateFragment : Fragment() {
    var _binding: FragmentCareInfoUpdateBinding? = null
    val binding get() = _binding!!
    var bitmap: Bitmap? = null
    var saveBitmap: Bitmap? = null

    //앨범에서 가져오기위한 런처
    val albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when(it.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                it.data?.apply {
                    val imageUri = this.data
                    imageUri?.let {
                        val cr = requireActivity().contentResolver
                        bitmap = MediaStore.Images.Media.getBitmap(cr, it)
                        saveBitmap = bitmap
                        binding.inputImageView.setImageBitmap(bitmap)
                        val saveCapture = activity as MainActivity
                        saveCapture.saveFile(bitmap!!)
                    }
                }
            }
            AppCompatActivity.RESULT_CANCELED -> {

            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, asavedInstanceState: Bundle?): View? {
        _binding = FragmentCareInfoUpdateBinding.inflate(inflater, container, false)
        initView()
        setView()


        binding.infoUpdateButton.setOnClickListener {
            updateCare()

        }


        PermissionX.init(this).permissions(android.Manifest.permission.CAMERA)
            .request{ allGranted, grantedList, deniedList ->
                if(allGranted){

                }else{

                }
            }

        return binding.root
    }

    fun setView(){
        AppData.loginData?.apply{
            this.careImage?.let {
                val uri = Uri.parse("http://192.168.0.215:8001${careImage}")
                Glide.with(binding.inputImageView).load(uri).into(binding.inputImageView)
            }
            binding.updateName.setText(AppData.loginData?.careId)
            binding.updateAddress.setText(AppData.loginData?.careAddress)

        }
    }

    //뷰 초기화
    fun initView() {
        //앨범에서 가져오기 버튼 눌렀을 때
        binding.addProfileButton7.setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
            albumIntent.type = "image/*"
            albumLauncher.launch(albumIntent)
        }

    }

    //회원 정보 수정 요청하기
    fun updateCare() {
        val careNo = AppData.loginData?.careNo
        val carePw1 = binding.checkPw.text.toString()
        val carePw2 = AppData.loginData?.carePw
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
                    AppData.loginData?.careName = careName
                    AppData.loginData?.careAddress = careAddress
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