package com.lx.project5

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.lx.api.BasicClient
import com.lx.data.*
import com.lx.project5.databinding.ActivityMainBinding
import com.permissionx.guolindev.PermissionX
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var locationClient: FusedLocationProviderClient? = null


    lateinit var map: GoogleMap

    var myMarker: Marker? = null


    enum class ScreenItem {
        ITEM1,
        ITEM2,
        ITEM3,
        ITEMmyPage,
        ITEMcareInfo,
        ITEMassess,
        ITEMcareMain,
        ITEMcareTodolist,
        ITEMcomplete,
        ITEMjoin1,
        ITEMjoin2,
        ITEMlogin,
        ITEMupdate,
        ITEMpay,
        ITEMreservation,
        ITEMwrite,
        ITEMwriteList,
        ITEMdogInfo,
        ITEMmemInfo,
        ITEMend,
        ITEMvideo,
        ITEMchoiceRegister,
        ITEMchoiceRegisterInfo

    }

    val dateFormat1 = SimpleDateFormat("yyyyMMddHHmmss")
    var filename: String? = null

    val bundle = Bundle()

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if(currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardView.setOnClickListener{
            infoView()
        }


        //?????? ?????? ????????? ????????????
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tab1 -> {
                    onFragmentChanged(ScreenItem.ITEM1)
                }
                R.id.tab2 -> {
                    onFragmentChanged(ScreenItem.ITEMvideo)
                }
                R.id.tab3 -> {
                    onFragmentChanged(ScreenItem.ITEM3)
                }
                R.id.tab4 -> {
                    if(AppData.loginData?.careId == null){
                        onFragmentChanged(ScreenItem.ITEMlogin)
                    }else if (AppData.loginData?.careId != null){
                        onFragmentChanged(ScreenItem.ITEMmyPage)
                    }

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        //????????? ?????? ??? ??? ?????? ????????????
        // onFragmentChanged(ScreenItem.ITEM1)

        binding.cardView.visibility = View.GONE
        // ???????????? ????????????
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    showToast("?????? ?????? ?????????.")
                } else {
                    showToast("????????? ?????? ??????.")
                }
            }

        // ?????? ???????????????
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it

            // ??? ?????? ????????????
            requestLocation()

            // ?????? ?????? ??? ??????
            map.setOnMapClickListener {
                //showToast("?????? ????????? : ${it.latitude}, ${it.longitude}")
                //????????? ????????????
                binding.cardView.visibility = View.GONE
            }

            // ???????????? ?????? ?????? ??????
            map.setOnCameraIdleListener {
                val bounds = map.projection.visibleRegion.latLngBounds
                //showToast("????????? : ${bounds.northeast}, ${bounds.southwest}")

                val zoomLevel = map.cameraPosition.zoom
                println("zoomLevel : ${zoomLevel}")
            }

            //??????
            showNearLocationMarker(map)

        }

    }
    fun onFragmentChanged(index: MainActivity.ScreenItem) {
        when(index) {
            ScreenItem.ITEM1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, FirstFragment()).commit()
            }
            ScreenItem.ITEM2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, SecondFragment()).commit()
            }
            ScreenItem.ITEM3 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, ThirdFragment()).commit()
            }
            ScreenItem.ITEMmyPage -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, MyPageFragment()).commit()
            }
            ScreenItem.ITEMcareInfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CareInfoFragment()).commit()
            }
            ScreenItem.ITEMassess -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, AssessFragment()).commit()
            }
            ScreenItem.ITEMcareMain -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CareMainFragment()).commit()
            }
            ScreenItem.ITEMcareTodolist -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CareTodolistFragment()).commit()
            }
            ScreenItem.ITEMcomplete -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CompleteFragment()).commit()
            }
            ScreenItem.ITEMjoin1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Join1Fragment()).commit()
            }
            ScreenItem.ITEMjoin2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, Join2Fragment()).commit()
            }
            ScreenItem.ITEMlogin -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
            }
            ScreenItem.ITEMupdate -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CareInfoUpdateFragment()).commit()
            }
            ScreenItem.ITEMpay -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment()).commit()
            }
            ScreenItem.ITEMreservation -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, ReservationFragment()).commit()
            }
            ScreenItem.ITEMwrite -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, WriteFragment()).commit()
            }
            ScreenItem.ITEMwriteList -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, WriteListFragment()).commit()
            }
            ScreenItem.ITEMdogInfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, DogInfoFragment()).commit()
            }
            ScreenItem.ITEMmemInfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, MemberInfoFragment()).commit()
            }
            ScreenItem.ITEMend -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, EndFragment()).commit()
            }
            ScreenItem.ITEMvideo -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, VideoFragment()).commit()
            }
            ScreenItem.ITEMchoiceRegister -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, ChoiceRegisterFragment()).commit()
            }
            ScreenItem.ITEMchoiceRegisterInfo -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CareInfoFragment()).commit()
            }

        }

    }

    fun showNearLocationMarker(map: GoogleMap) {
        BasicClient.api.getMemberRequestList(
            requestCode = "1001",
            awrn = WriteRegisterData.awrn.toString()
        ).enqueue(object : Callback<MemberRequestResponse> {
            override fun onResponse(call: Call<MemberRequestResponse>, response: Response<MemberRequestResponse>) {
                Log.v("lastkingdom","?????? ?????? ????????? ?????? ??????")
                val jsonArray = JSONArray(response.body()?.data)
                for (i in 0 until jsonArray.length()) {
                    Log.v("lastkingdom","?????? ?????? for??? ??????")
                    var latitude = response.body()?.data?.get(i)?.writeX
                    var longitude = response.body()?.data?.get(i)?.writeY

                    Log.v("lastkingdom","?????? ?????? ${latitude.toString()}")
                    Log.v("lastkingdom","?????? ?????? ${longitude.toString()}")

                    Log.v("lastkingdom","2")
                    // 1. ?????? ?????? ?????? (????????? ??????)
                    var makerOptions = MarkerOptions()
                    makerOptions // LatLng??? ?????? ???????????? ???????????? ????????? ?????? ??????.
                        .position(LatLng(latitude!!, longitude!!))
                        .title(response.body()?.data?.get(i)?.awrn.toString()) // ?????????.

                    // 2. ?????? ?????? (????????? ?????????)
                    map.addMarker(makerOptions)

                    // ????????????
                    map.setOnMarkerClickListener {

                        response.body()?.data?.get(i)?.apply {
                            binding.className.text = this.memberName.toString()
                            binding.classAddress.text = this.memberAddress.toString()
                            binding.classSelf.text = this.assignTitle.toString()
                            WriteRegisterData.memberNo = this.memberNo
                            WriteRegisterData.dogNo = this.dogNo
                            WriteRegisterData.awrn = this.awrn

                            //bundle ????????? ??????
                            bundle.putString("memberName",this.memberName)
                            bundle.putString("memberAddress",this.memberAddress)
                            bundle.putString("assignTitle",this.assignTitle)

                            Log.v("???", "${WriteRegisterData.assignContent}")

                        }

                        AppData.goIndex = 1
                        binding.cardView.visibility = View.VISIBLE

                        true

                    }
                }
            }
            override fun onFailure(call: Call<MemberRequestResponse>, t: Throwable) {
                Log.v("lastkingdom","?????? ?????? ????????? ?????? ??????")
            }
        })
    }

    fun infoView() {
        // ?????? ?????? ??????
        BasicClient.api.getMemberInfo(
        requestCode = "1001",
        memberNo = WriteRegisterData?.memberNo.toString()

        ).enqueue(object : Callback<MemberListResponse> {
            override fun onResponse(call: Call<MemberListResponse>, response: Response<MemberListResponse>) {
                AppData.memberData = MemberData()
                response.body()?.data?.get(0)?.apply {
                    AppData.memberData?.memberAddress = this.memberAddress
                    AppData.memberData?.memberImage = this.memberImage
                    AppData.memberData?.memberName = this.memberName
                    AppData.memberData?.memberNo = this.memberNo

                    // ????????? ??????
                    bundle.putString("memberAddress",this.memberAddress)
                    bundle.putString("memberImage",this.memberImage)
                    bundle.putString("memberName",this.memberName)
                    bundle.putString("memberNo",this.memberNo)

                    Log.v("?????????", "${this}")
                }
            }
            override fun onFailure(call: Call<MemberListResponse>, t: Throwable) {

            }

        })

        // ????????? ?????????
        BasicClient.api.getDogInfo(
            requestCode = "1001",
            dogNo = WriteRegisterData?.dogNo.toString()

        ).enqueue(object : Callback<DogListResponse> {
            override fun onResponse(call: Call<DogListResponse>, response: Response<DogListResponse>) {
                AppData.dogData = PetData()
                response.body()?.data?.get(0)?.apply {
                    AppData.dogData?.dogNo = this.dogNo
                    AppData.dogData?.dogAge = this.dogAge
                    AppData.dogData?.dogImage = this.dogImage
                    AppData.dogData?.dogBreed = this.dogBreed
                    AppData.dogData?.dogCharacter = this.dogCharacter
                    AppData.dogData?.dogEducation = this.dogEducation
                    AppData.dogData?.dogGender = this.dogGender

                    // ????????? ??????
                    bundle.putString("dogNo",this.dogNo)
                    bundle.putString("dogAge",this.dogAge)
                    bundle.putString("dogImage",this.dogImage)
                    bundle.putString("dogBreed",this.dogBreed)
                    bundle.putString("dogCharacter",this.dogCharacter)
                    bundle.putString("dogEducation",this.dogEducation)
                    bundle.putString("dogGender",this.dogGender)

                    Log.v("?????????", "${this}")
                }
            }
            override fun onFailure(call: Call<DogListResponse>, t: Throwable) {

            }

        })

        //
        BasicClient.api.getMemberRequestList(
            requestCode = "1001",
            awrn = WriteRegisterData?.awrn.toString()

        ).enqueue(object : Callback<MemberRequestResponse> {
            override fun onResponse(call: Call<MemberRequestResponse>, response: Response<MemberRequestResponse>) {
                AppData.writeRegisterData = WriteRegisterData()
                response.body()?.data?.get(0)?.apply {
                    WriteRegisterData?.awrn = this.awrn
                    WriteRegisterData?.memberNo = this.memberNo
                    WriteRegisterData?.dogNo = this.dogNo
                    WriteRegisterData?.assignTitle = this.assignTitle
                    WriteRegisterData?.assignContent = this.assignContent
                    WriteRegisterData?.startTime = this.startTime
                    WriteRegisterData?.endTime = this.endTime


                    // ????????? ??????
                    bundle.putString("awrn",this.awrn.toString())
                    bundle.putString("memberNo",this.memberNo.toString())
                    bundle.putString("dogNo",this.dogNo.toString())
                    bundle.putString("assignTitle",this.assignTitle)
                    bundle.putString("assignContent",this.assignContent)
                    bundle.putString("startTime",this.startTime)
                    bundle.putString("endTime",this.endTime)

                    Log.v("?????????", "${this}")
                }
            }
            override fun onFailure(call: Call<MemberRequestResponse>, t: Throwable) {

            }

        })

        replaceFragment(bundle)

        onFragmentChanged(ScreenItem.ITEMcareInfo)
    }




    fun requestLocation() {

        try {
            // ?????? ????????? ????????? ?????? ????????????
            locationClient?.lastLocation?.addOnSuccessListener {

            }


            // ????????????????????? ?????????
            locationClient = LocationServices.getFusedLocationProviderClient(this)

            // ???????????? ????????? ??? ????????? ?????????
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 30 * 1000
            }

            // ???????????? ????????? ??? ???????????? ???????????? ??????
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for ((index, location) in result.locations.withIndex()) {

                        showCurrentLocation(location)
                    }
                }
            }


            // ??? ?????? ??????
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        } catch(e:SecurityException) {
            e.printStackTrace()
        }
    }

    // ??? ????????? ?????? ????????????
    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17.0f))

        showMarker(curPoint)

    }

    fun showMarker(curPoint: LatLng) {
        myMarker?.remove()

        MarkerOptions().also {
            it.position(curPoint)
            it.title("?????????")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.dogmarker))


            myMarker = map.addMarker(it)
            myMarker?.tag = "1001"
        }

    }

    //??????????????? ?????? ????????? ????????????
    fun saveFile(bitmap: Bitmap) {
        filename = dateFormat1.format(Date()) + ".jpg"
        bitmap?.apply {
            openFileOutput(filename, Context.MODE_PRIVATE).use {
                this.compress(Bitmap.CompressFormat.JPEG, 100, it)
                it.close()

                showToast("???????????? ????????? ????????? : ${filename}")

                uploadFile(filename!!)
            }
        }
    }
    fun uploadFile(filename:String){
        // ????????? ????????? ?????? ????????? filePart??? ?????????
        val file = File("${filesDir}/${filename}")
        val filePart = MultipartBody.Part.createFormData(
            "photo",
            filename,
            file.asRequestBody("images/jpg".toMediaTypeOrNull())
        )
        // ?????? ??????????????? ?????? ??????
        val params = hashMapOf<String, String>()

        //api ??? ?????? ????????? ??????
        BasicClient.api.uploadFile(
            file = filePart,
            params = params
        ).enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(call: Call<FileUploadResponse>, response: Response<FileUploadResponse>) {
                response.body()?.output?.filename?.apply{
                    AppData.filepath = this
                }


            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {

            }


        })
    }

    // @@@@@@@@test
    fun replaceFragment(bundle: Bundle) {
        val destination = CareInfoFragment()
        destination.arguments = bundle      // ???????????? ?????????
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, destination)
            .commit()
    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}