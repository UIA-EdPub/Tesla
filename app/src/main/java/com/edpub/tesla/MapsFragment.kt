package com.edpub.tesla

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.edpub.tesla.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import java.io.IOException


class MapsFragment : Fragment() {

    private val LOCATION_REQUEST_CODE = 1
    private val DEFAULT_ZOOM = 18

    private lateinit var map: GoogleMap
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(28.56172208815701, 77.28194072328036)

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var binding: FragmentMapsBinding

    private lateinit var mapFragment : SupportMapFragment

    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        googleMap.uiSettings.apply {
            isCompassEnabled = false
            isMapToolbarEnabled = false
            isIndoorLevelPickerEnabled = false
        }

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
        mapFragment = childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
        checkPermission()
        initView()
    }

    private fun initView() {

        binding.svLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // on below line we are getting the
                // location name from search view.
                val location: String = binding.svLocation.query.toString()

                // below line is to create a list of address
                // where we will store the list of all address.
                var addressList: List<Address>? = null

                // checking if the entered location is null or not.
                if (location != null || location == "") {
                    // on below line we are creating and initializing a geo coder.
                    val geocoder = Geocoder(requireContext())
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    if(addressList!!.size==0){
                        Snackbar.make(requireContext(), binding.cvZoomIn, "Location cannot be found", Snackbar.LENGTH_LONG).show()
                    }else{

                        val address = addressList!![0]
                        // on below line we are creating a variable for our location
                        // where we will add our locations latitude and longitude.
                        val latLng = LatLng(address.latitude, address.longitude)

                        map.clear()

                        // on below line we are adding marker to that position.
                        map.addMarker(MarkerOptions().position(latLng).title(location))

                        // below line is to animate camera to that position.
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                    }


                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        // at last we calling our map fragment to update.
        binding.cvZoomIn.setOnClickListener {
            zoomIn()
        }
        binding.cvZoomOut.setOnClickListener {
            zoomOut()
        }
        binding.cvTakeSs.setOnClickListener {

            map.clear()

            val snapshotReadyCallback : GoogleMap.SnapshotReadyCallback = GoogleMap.SnapshotReadyCallback { selectedScreenShot ->
                val uri = UtilityFunctions.getUriFromBitmap(selectedScreenShot!!, requireActivity().contentResolver)
                val intent = Intent(requireActivity(), EditMapAreaActivity::class.java)
                intent.putExtra("mapImagePath", uri.toString())
                startActivity(intent)
            }

            val onMapLoadedCallback : GoogleMap.OnMapLoadedCallback = GoogleMap.OnMapLoadedCallback {
                map.snapshot(snapshotReadyCallback)
            }

            map.setOnMapLoadedCallback(onMapLoadedCallback)
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

    override fun onResume() {
        if(lastKnownLocation!=null){
            map.addMarker(
                MarkerOptions().position(
                    LatLng(
                        lastKnownLocation!!.latitude,
                        lastKnownLocation!!.longitude
                    )
                ).title("Your Current Location")
            )
        }
        super.onResume()
    }
}