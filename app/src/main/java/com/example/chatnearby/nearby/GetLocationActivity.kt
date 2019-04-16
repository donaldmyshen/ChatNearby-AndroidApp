package com.example.chatnearby.nearby

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.example.chatnearby.R
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_get_location.*

class GetLocationActivity : AppCompatActivity() {

    private var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    private var REQUEST_CODE = 1001
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback : LocationCallback

    private var locationUpdateState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_location)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        createLocationRequest()

        locationCallback = object :LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                var lastLocation = p0.lastLocation
                var myLat: Double = lastLocation.latitude

                var myLong: Double = lastLocation.longitude

                textView.text = "Lat: ${myLat} Long: ${myLong}"
            }
        }

        startLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        if(locationUpdateState) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        this.fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        this.fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_CODE) {
            if(grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()

        locationRequest.interval = 10000

        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        var builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
    }


    // distance calculate algorithm
    private fun getDistandce(myLat: Double, myLong: Double, hisLat: Double, hisLong: Double) : Double {
        // 角度转为弧度
        var myNS :Double = myLat * Math.PI
        var myEW: Double = myLong * Math.PI
        var hisNS: Double = hisLat * Math.PI
        var hisEW: Double = hisLong * Math.PI

        var dEW = myEW - hisEW

        if (dEW > Math.PI)
            dEW = 2 * Math.PI - dEW
        else if (dEW < - Math.PI)
            dEW = 2 * Math.PI - dEW

        var dx = 6370693.5 * Math.cos(myNS) * dEW// 东西方向长度(在纬度圈上的投影长度)
        var dy = 6370693.5 * (myNS - hisNS) // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        var distance = Math.sqrt(dx * dx + dy * dy);
        return distance
    }
}


