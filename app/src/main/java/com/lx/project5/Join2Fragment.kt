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
import com.lx.data.MemberListResponse
import com.lx.project5.databinding.FragmentJoin1Binding
import com.lx.project5.databinding.FragmentJoin2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Join2Fragment : Fragment() {
    var _binding: FragmentJoin2Binding? = null
    val binding get() = _binding!!

    var bitmap: Bitmap? = null
    var saveBitmap: Bitmap? = null

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
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
            checkPw()
        }

        binding.idCheckButton.setOnClickListener {
            checkId()
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
            postMemberAdd()
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

        BasicClient.api.postMemberCheckId(
            requestCode = "1001",
            memberId = registerId
        ).enqueue(object: Callback<MemberListResponse> {
            override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>){
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

            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {

            }

        })
    }

    //멤버리스트 추가 [파라미터]
    fun postMemberAdd(){

        var registerId = binding.registerId.text.toString()
        var registerName = binding.registerName.text.toString()
        var registerPw = binding.registerPw.text.toString()


        BasicClient.api.postMemberAdd(
            requestCode = "1001",
            memberId = registerId,
            memberPw =registerPw,
            memberName =registerName

        ).enqueue(object:Callback<MemberListResponse>{
            override fun onResponse(call: Call<MemberListResponse>,response: Response<MemberListResponse>){
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEM1)
            }

            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {
                (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEM1)
            }

        })
    }

    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}