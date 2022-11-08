package com.lx.project5

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.lx.project5.databinding.ActivityMainBinding
import com.permissionx.guolindev.PermissionX

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    var locationClient: FusedLocationProviderClient? = null

    lateinit var map: GoogleMap

    var myMarker: Marker? = null

    enum class ScreenItem {
        ITEM1,
        ITEM2,
        ITEM3,
        ITEM4,
        ITEM5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardView.setOnClickListener{
            onFragmentChanged(ScreenItem.ITEM5)
        }
        //하단 탭의 버튼을 눌렀을때
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tab1 -> {
                    onFragmentChanged(ScreenItem.ITEM1)
                }
                R.id.tab2 -> {
                    onFragmentChanged(ScreenItem.ITEM2)
                }
                R.id.tab3 -> {
                    onFragmentChanged(ScreenItem.ITEM3)
                }
                R.id.tab4 -> {
                    onFragmentChanged(ScreenItem.ITEM4)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        //화면이 보일 때 첫 화면 보여주기
       // onFragmentChanged(ScreenItem.ITEM1)

        binding.cardView.visibility = View.GONE
        // 위험권한 요청하기
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    showToast("모든 권한 부여됨.")
                } else {
                    showToast("거부된 권한 있음.")
                }
            }

        // 지도 초기화하기
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it

            // 내 위치 요청하기
            requestLocation()

            // 마커 클릭 시 처리
            map.setOnMarkerClickListener {
                // showToast("마커 클릭됨 : ${it.tag}, ${it.title}")

                // 필요시 다른 화면으로 이동 (tag 정보를 이용해서 구분함)
                binding.cardView.visibility = View.VISIBLE


                true
            }

            // 지도 클릭 시 처리
            map.setOnMapClickListener {
                showToast("지도 클릭됨 : ${it.latitude}, ${it.longitude}")
                //카드뷰 안보이게
                binding.cardView.visibility = View.GONE
            }

            // 보고있는 지도 영역 구분
            map.setOnCameraIdleListener {
                val bounds = map.projection.visibleRegion.latLngBounds
                showToast("좌상단 : ${bounds.northeast}, ${bounds.southwest}")

                val zoomLevel = map.cameraPosition.zoom
                println("zoomLevel : ${zoomLevel}")
            }

        }


    }
    fun onFragmentChanged(index:ScreenItem) {
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
            ScreenItem.ITEM4 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, MyPageFragment()).commit()
            }
            ScreenItem.ITEM5 -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CareInfoFragment()).commit()
            }
        }

    }
    fun requestLocation() {

        try {
            // 가장 최근에 확인된 위치 알려주기
            locationClient?.lastLocation?.addOnSuccessListener {

            }


            // 위치클라이언트 만들기
            locationClient = LocationServices.getFusedLocationProviderClient(this)

            // 내위치를 요청할 때 필요한 설정값
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 30 * 1000
            }

            // 내위치를 받았을 때 자동으로 호출되는 함수
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for ((index, location) in result.locations.withIndex()) {

                        showCurrentLocation(location)
                    }
                }
            }

            // 내 위치 요청
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        } catch(e:SecurityException) {
            e.printStackTrace()
        }
    }

    // 내 위치의 지도 보여주기
    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17.0f))

        showMarker(curPoint)

    }

    fun showMarker(curPoint: LatLng) {
        myMarker?.remove()

        MarkerOptions().also {
            it.position(curPoint)
            it.title("내위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.dogmarker))


            myMarker = map.addMarker(it)
            myMarker?.tag = "1001"
        }

    }

    fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}