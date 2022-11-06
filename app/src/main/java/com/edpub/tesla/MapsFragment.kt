package com.edpub.tesla

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edpub.tesla.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap

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

        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        initView()

    }

    private fun initView(){
        binding.cvZoomIn.setOnClickListener {
            zoomIn()
        }
        binding.cvZoomOut.setOnClickListener {
            zoomOut()
        }
    }

    fun zoomIn(){
        map.animateCamera(CameraUpdateFactory.zoomIn())
    }
    fun zoomOut(){
        map.animateCamera(CameraUpdateFactory.zoomOut())
    }
}