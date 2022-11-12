package com.lx.project5

import android.annotation.SuppressLint
import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.api.BasicClient
import com.lx.project5.databinding.FragmentVideoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 비디오 리스트
class VideoFragment : Fragment() {
    // 바인드처리
    var _binding: FragmentVideoBinding? = null
    val binding get() = _binding!!

    var video: VideoView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)

        video = binding.videoView
        video?.setVideoURI(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))

        binding.button.setOnClickListener{
            video!!.start()
        }
        binding.buttonSTOP.setOnClickListener{
            video!!.pause()
        }




        return binding.root
    }





    /** 토스트 메시지 보여주기 */
    fun showToast(message:String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}