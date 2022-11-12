package com.lx.project5

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.lx.project5.databinding.ActivityLocalBinding
import com.permissionx.guolindev.PermissionX

class LocalActivity : AppCompatActivity() {
    lateinit var binding: ActivityLocalBinding

    var locationClient:FusedLocationProviderClient? = null
    lateinit var map:GoogleMap
    var myMarker:Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //위험권한 요청하기
        PermissionX.init(this)
            .permissions(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            .request {allGranted, grantedList, deniedList ->
                if(allGranted) {
                    showToast("권한부여")
                } else {

                }
            }

        //지도 초기화하기
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it

            //내 위치 요청하기
            //requestLocation()

            //마커 클릭시 처리
//            map.setOnMarkerClickListener {
//                //showToast("마커클릭 : ${it.tag}, ${it.title}")
//                binding.output12.text = "마커클릭 : ${it.tag}, ${it.title}"
//
//                true
//            }

            //지도 클릭 시 처리
            map.setOnMapClickListener {
                //showToast("지도 클릭 : ${it.latitude}, ${it.longitude}")

                binding.outputLat.text = "${it.latitude}"
                binding.outputLng.text = "${it.longitude}"
                showMarker(LatLng(it.latitude, it.longitude))
            }

            //보고있는 지도 영역 구분
//            map.setOnCameraIdleListener {
//                val bounds = map.projection.visibleRegion.latLngBounds
//                showToast("좌상단 : ${bounds.northeast}, ${bounds.southwest}")
//
//                val zoomLevel = map.cameraPosition.zoom
//                println("zoonLevel : ${zoomLevel}")
//            }
        }

        //내 위치 확인 버튼
        binding.startButton.setOnClickListener {
            requestLocation()
        }

        //상품등록으로 돌아가기 버튼
        binding.backButton.setOnClickListener {
            AppData.lat = binding.outputLat.text.toString()
            AppData.lng = binding.outputLng.text.toString()
            finish()
        }
    }

    fun requestLocation() {
        try {
            //가장 최근에 확인된 위치 알려주기
//            locationClient?.lastLocation?.addOnSuccessListener {
//                binding.output12.text = "최근위치 : ${it.latitude}, ${it.longitude}"
//            }
            //위치 클리어언트 만들기
            locationClient = LocationServices.getFusedLocationProviderClient(this)

            //내위치를 요청할 때 필요한 설정값
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 30 * 1000
            }

            //내 위치를 받았을 때 자동으로 호출되는 함수
            val locationCallback = object  : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)

                    for((index, location) in result.locations.withIndex()) {
                        //binding.output12.text = "내 위치 : ${location.latitude}, ${location.longitude}"
                        showCurrentLocation(location)
                    }
                }
            }

            //내위치 요청
            locationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        } catch (e:SecurityException) {
            e.printStackTrace()
        }
    }

    //내 위치의 지도 보여주기
    fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17.0f))

        // showMarker(curPoint)
    }

    fun showMarker(curPoint:LatLng) {
        myMarker?.remove()

        MarkerOptions().also {
            it.position(curPoint)
            it.title("내 위치")
            it.icon(BitmapDescriptorFactory.fromResource(R.drawable.cancel))

            myMarker = map.addMarker(it)
            myMarker?.tag = "현재위치"
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}