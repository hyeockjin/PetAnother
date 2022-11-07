package com.lx.project5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.lx.project5.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewList()

        binding.exitButton.setOnClickListener {
            finishSplashActivity()
        }

    }
    companion object {
//        private const val DURATION : Long = 2000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    //  광고 슬라이드 처리 viewPage2
    fun viewList() {
        val fragmentList = listOf(viewPager1(),viewPager2(),viewPager3())
        val viewAdapter = ViewPagerAdapter(this)
        viewAdapter.fragments = fragmentList
        binding.vPager2.adapter = viewAdapter
    }
//  viewfragment3에서 이 함수를 호출해서 실행하여 splash화면을 끝내줄게요
    fun finishSplashActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }
}