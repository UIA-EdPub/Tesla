package com.edpub.tesla

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.edpub.tesla.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.security.Permissions

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.security.Permission


class MapsFragment : Fragment() {

    private val LOCATION_REQUEST_CODE = 1
    private val DEFAULT_ZOOM = 18

    private lateinit var map: GoogleMap
    private lateinit var cameraPosition: CameraPosition
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(28.56172208815701, 77.28194072328036)

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var binding: FragmentMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        val centralLib = LatLng(28.56172208815701, 77.28194072328036)
        googleMap.addMarker(MarkerOptions().position(centralLib).title("Marker in Central Library"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(centralLib))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        checkPermission()
        initView()

    }

    private fun initView(){
        binding.cvZoomIn.setOnClickListener {
            zoomIn()
        }
        binding.cvZoomOut.setOnClickListener {
            zoomOut()
        }
        binding.bTakeSs.setOnClickListener {
            binding.llMap.drawToBitmap()
        }

        binding.cvLocateMe.setOnClickListener {
            Log.i("fuckloction", "cv click")
            getDeviceLocation()
        }
    }

    fun zoomIn(){
        map.animateCamera(CameraUpdateFactory.zoomIn())
    }
    fun zoomOut(){
        map.animateCamera(CameraUpdateFactory.zoomOut())
    }

    private fun checkPermission(){
        if(ActivityCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted = true
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
        }
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        Log.i("fucklocation", "cv click")
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {

                        Log.d("fucklocation", "Current location is null. Using defaults.")
                        Log.e("fucklocation", "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
//                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("fucklocation", e.message, e)
        }
    }

}