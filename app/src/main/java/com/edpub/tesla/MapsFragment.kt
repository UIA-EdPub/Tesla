package com.edpub.tesla

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.edpub.tesla.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.File
import java.io.FileOutputStream


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
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        getDeviceLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    private fun initView() {
        binding.cvZoomIn.setOnClickListener {
            zoomIn()
        }
        binding.cvZoomOut.setOnClickListener {
            zoomOut()
        }
        binding.bTakeSs.setOnClickListener {

            try{
                val roofImage = binding.llMap.drawToBitmap()
                Log.i("mapimage", roofImage.toString())
                binding.llMap.visibility=View.INVISIBLE
                val roofDrawable: Drawable = BitmapDrawable(resources, roofImage)
                binding.ivTest.setImageDrawable(roofDrawable)
            }catch (e: java.lang.Exception){
                Log.i("mapimage", e.toString())
            }

        }

        binding.cvLocateMe.setOnClickListener {
            getDeviceLocation()
        }
    }

    fun zoomIn() {
        map.animateCamera(CameraUpdateFactory.zoomIn())
    }

    fun zoomOut() {
        map.animateCamera(CameraUpdateFactory.zoomOut())
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        }
    }

    private fun askStoragePermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(WRITE_EXTERNAL_STORAGE),
                LOCATION_REQUEST_CODE
            )
        }
        return true
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )

                            map.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    )
                                ).title("Your Current Location")
                            )

                        }
                    } else {


                        map.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map.addMarker(
                            MarkerOptions().position(
                                defaultLocation
                            ).title("Jamia Central Library")
                        )
//                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("MapLoading", e.message, e)
        }
    }

}