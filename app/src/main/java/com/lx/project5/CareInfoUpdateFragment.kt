package com.lx.project5

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lx.api.BasicClient
import com.lx.data.CareListResponse
import com.lx.project5.AppData.Companion.loginData
import com.lx.project5.databinding.FragmentCareInfoUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

//사진은 작게나오는데 공간을 많이 차지함
class CareInfoUpdateFragment : Fragment() {
    var _binding: FragmentCareInfoUpdateBinding? = null
    val binding get() = _binding!!

    var bitmap: Bitmap? = null
    var saveBitmap: Bitmap? = null
    var careImage: String? = "1"

    val dateFormat1 = SimpleDateFormat("yyyyMMddHHmmss")
    val dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm")

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

        binding.infoUpdateButton.setOnClickListener {
            updateCare()

        }
        initView()

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

    //뷰 초기화
    fun initView() {
        //앨범에서 가져오기 버튼 눌렀을 때
        binding.button10.setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
            albumIntent.type = "image/*"
            albumLauncher.launch(albumIntent)
        }
//        //사진찍기버튼
//        binding.button11.setOnClickListener {
//            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            captureLauncher.launch(captureIntent)
//        }

    }
    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}