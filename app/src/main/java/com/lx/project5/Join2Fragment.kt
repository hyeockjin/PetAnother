package com.lx.project5

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lx.api.BasicClient
import com.lx.data.CareListResponse
import com.lx.project5.databinding.FragmentJoin2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Join2Fragment : Fragment() {
    var _binding: FragmentJoin2Binding? = null
    val binding get() = _binding!!

    var bitmap: Bitmap? = null
    var saveBitmap: Bitmap? = null
    var careImage: String? = "1"

    //앨범에서 가져오기위한 런처
    val albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        toast("앨범에서 돌아옴")

        when(it.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                it.data?.apply {
                    val imageUri = this.data
                    imageUri?.let {
                        val cr = requireActivity().contentResolver
                        bitmap = MediaStore.Images.Media.getBitmap(cr, it)
                        saveBitmap = bitmap
                        binding.profileImageView.setImageBitmap(bitmap)
                        val saveCapture = activity as MainActivity
                        saveCapture.saveFile(bitmap!!)
                    }
                }
            }
            AppCompatActivity.RESULT_CANCELED -> {
                toast("앨범 선택 취소")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJoin2Binding.inflate(inflater, container, false)
        initView()

        binding.nextButton2.setOnClickListener {
            checkPw()
        }

        binding.idCheckButton.setOnClickListener {
            checkId()
        }

        binding.locationButton.setOnClickListener {
            val locationIntent= Intent(activity,LocalActivity::class.java)
            startActivity(locationIntent)
        }

        binding.imageButton4.setOnClickListener{
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMjoin1)
        }

        return binding.root
    }

    //뷰 초기화
    fun initView() {
        //앨범에서 가져오기 버튼 눌렀을 때
        binding.addProfileButton2.setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_GET_CONTENT)
            albumIntent.type = "image/*"
            albumLauncher.launch(albumIntent)
        }

    }

    //비밀번호 확인 맞을때만 회원가입 가능하게 하기
    fun checkPw() {
        var registerPw = binding.registerPw.text.toString()
        var pwCheck = binding.pwCheck.text.toString()

        if(registerPw.equals(pwCheck)) {
            postCareAdd()
        } else {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("회원가입")
            builder.setMessage("비밀번호를 다시 입력해주세요.")
            builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                toast("Positive")
            }
            builder.show()
        }
    }

    //중복체크
    fun checkId() {
        var registerId = binding.registerId.text.toString()

        BasicClient.api.postCareCheckId(
            requestCode = "1001",
            careId = registerId
        ).enqueue(object: Callback<CareListResponse> {
            override fun onResponse(call: Call<CareListResponse>, response: Response<CareListResponse>){
                val checkId = response.body()?.header?.total.toString()

                if(checkId == "1"){
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("중복체크")
                    builder.setMessage("이미 있는 아이디입니다.")
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        toast("Positive")
                    }
                    builder.show()
                    binding.registerId.setText("")
                }
                if(checkId == "0") {
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("중복체크")
                    builder.setMessage("사용 가능한 아이디입니다.")
                    builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        toast("Positive")
                    }
                    builder.show()
                }
            }

            override fun onFailure(call: Call<CareListResponse>, t: Throwable) {

            }

        })
    }

    //돌보미리스트 추가 [파라미터]
    fun postCareAdd(){

        var registerId = binding.registerId.text.toString()
        var registerName = binding.registerName.text.toString()
        var registerPw = binding.registerPw.text.toString()
        var registerExperience = binding.petExperienceInput.text.toString()
        var registerEducation = binding.petEducationInput.text.toString()
        val lat = AppData.lat?.toDouble()
        val lng = AppData.lng?.toDouble()


        BasicClient.api.postCareAdd(
            requestCode = "1001",
            careId = registerId,
            carePw = registerPw,
            careName = registerName,
            careExperience = registerExperience,
            careEducation = registerEducation,
            careImage = careImage!!,
            careApproval = "1",
            lat = lat!!,
            lng = lng!!

        ).enqueue(object:Callback<CareListResponse>{
            override fun onResponse(call: Call<CareListResponse>,response: Response<CareListResponse>){
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMlogin)
            }

            override fun onFailure(call: Call<CareListResponse>, t: Throwable) {
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMlogin)
            }

        })
    }

    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}