package com.lx.project5

import android.R
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.lx.project5.databinding.FragmentVideoBinding


// 비디오 리스트
class VideoFragment : Fragment() {
    // 바인드처리
    var _binding: FragmentVideoBinding? = null
    val binding get() = _binding!!

    var video: VideoView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)

        video = binding.videoView
        //val path =  com.lx.project5.R.raw.video_file
        val uri =
            Uri.parse("android.resource://" + activity?.getPackageName().toString() + "/" + com.lx.project5.R.raw.video_file)
        video!!.setVideoURI(uri)
        //영상재생
        binding.button.setOnClickListener{
            video!!.start()
        }
        //영상멈춤
        binding.buttonSTOP.setOnClickListener{
            video!!.pause()
        }
        //비디오 재생종료시 수강완료 메시지 출력
        video!!.setOnCompletionListener {
            showToast("수강 완료되었습니다. ")
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMmyPage)
        }





        return binding.root
    }




    /** 토스트 메시지 보여주기 */
    fun showToast(message:String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}


