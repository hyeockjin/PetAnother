package com.lx.project5

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lx.project5.AppData.Companion.selectedItem
import com.lx.project5.databinding.FragmentDogInfoBinding

class DogInfoFragment : Fragment() {
    var _binding: FragmentDogInfoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDogInfoBinding.inflate(inflater, container, false)

        initView()

        binding.backButton3.setOnClickListener {
            (activity as MainActivity).onFragmentChanged(MainActivity.ScreenItem.ITEMcareMain)
        }

        return binding.root
    }
    fun initView(){
        selectedItem?.apply{
            this.dogImage .let{
                val uri = Uri.parse("http://192.168.0.12:8001${dogImage}")
                Glide.with(binding.imageView4).load(uri).into(binding.imageView4)
            }
            binding.petName.text = "${selectedItem?.dogName}"
            binding.petAge.text = "${selectedItem?.dogAge}"
            binding.petGender.text = "${selectedItem?.dogGender}"
            binding.petBreed?.text = "${selectedItem?.dogBreed}"
            binding.petEducation.text = "${selectedItem?.dogEducation}"
            binding.petCharacter.text = "${selectedItem?.dogCharacter}"

        }
    }

}