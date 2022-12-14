package com.lx.project5

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lx.project5.databinding.FragmentSueBinding
import java.text.SimpleDateFormat

//사진이 너무크게나옴
class SueFragment : Fragment() {
    var _binding: FragmentSueBinding? = null
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
                        binding.imageButton2.setImageBitmap(bitmap)
                        val saveCapture = activity as MainActivity
                        saveCapture.saveFile(bitmap!!)
                    }
                }
            }
            AppCompatActivity.RESULT_CANCELED -> {

            }
        }
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSueBinding.inflate(inflater, container, false)

        initView()

        return binding.root
    }
    //뷰 초기화
    fun initView() {
        //앨범에서 가져오기 버튼 눌렀을 때
        binding.imageButton2.setOnClickListener {
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

}