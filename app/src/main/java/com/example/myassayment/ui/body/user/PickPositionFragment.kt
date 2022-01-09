package com.example.myassayment.ui.body.user

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentPickPositionBinding
import com.example.myassayment.databinding.FragmentUserBinding
import com.example.myassayment.viewmodels.AuthViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PickPositionFragment : Fragment() {
    private var _binding: FragmentPickPositionBinding?=null
    private val binding get() = _binding!!
    private var lati:String?=null
    private var lang:String?=null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val cairo = LatLng(30.04447670707573, 31.235290669369935)
        var marker=googleMap.addMarker(MarkerOptions().position(cairo))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cairo))
        googleMap.uiSettings.apply {
            isZoomControlsEnabled=true
        }

        googleMap.setOnMapClickListener {
            marker?.remove()
            marker=googleMap.addMarker(MarkerOptions().position(it))
            lati=it.latitude.toString()
            lang=it.longitude.toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentPickPositionBinding.inflate(layoutInflater)
        setUp()
        return binding.root
    }

    private fun setUp() {
        binding.btnBackPickpos.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.fabCheakCheakpos.setOnClickListener {
            val action=PickPositionFragmentDirections.actionPickPositionFragmentToUserFragment(
                lang = lang,
                lati = lati
            )
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}