package com.example.myassayment.ui.body

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myassayment.R
import com.example.myassayment.databinding.FragmentLocatonsBinding
import com.example.myassayment.maps.ClusterMarker
import com.example.myassayment.maps.MyClusterMangerRender
import com.example.myassayment.models.Doctor
import com.example.myassayment.utils.StatusResult
import com.example.myassayment.viewmodels.HomeViewModel
import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocatonsFragment : Fragment() {

    //vars
    private var _binding: FragmentLocatonsBinding?=null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var doctorsLocation:MutableList<Doctor>
    lateinit var mMyClusterManagerRender: MyClusterMangerRender
    lateinit var mClusterManger: ClusterManager<ClusterMarker>
    private val mClusterMarkers= mutableListOf<ClusterMarker>()

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.uiSettings.apply {
            isZoomControlsEnabled=true
        }
//        googleMap.setMinZoomPreference(10f)

        addMarkers(googleMap,doctorsLocation)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentLocatonsBinding.inflate(layoutInflater)
        setUp()

        return binding.root
    }

    private fun setUp() {
        homeViewModel.getDoctors()
        homeViewModel.doctor.observe(viewLifecycleOwner,{
            when{
                it is StatusResult.OnError ->{
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnLoading -> {
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                }
                it is StatusResult.OnSuccess ->{
                    doctorsLocation=it.data!!
                    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                    mapFragment?.getMapAsync(callback)
                }
            }
        })
    }

    @SuppressLint("PotentialBehaviorOverride")
    fun addMarkers(googleMap: GoogleMap, doctors: MutableList<Doctor>)
    {
        mClusterManger= ClusterManager(requireContext(),googleMap)
        mMyClusterManagerRender= MyClusterMangerRender(requireActivity(),googleMap,mClusterManger)
        mClusterManger.renderer=mMyClusterManagerRender
        googleMap.setOnMarkerClickListener(mClusterManger)
        mClusterManger.setOnClusterItemInfoWindowClickListener {
            val action=LocatonsFragmentDirections.actionLocatonsFragmentToDoctorInfoFragment(it.doctor)
            findNavController().navigate(action)
        }
        doctors.forEach {

            val clusterMarker=ClusterMarker(
                it.photo!!,
                it
            )
            mClusterMarkers.add(clusterMarker)

        }
        mClusterManger.addItems(mClusterMarkers)
        googleMap.setOnCameraIdleListener (mClusterManger)
        mClusterManger.cluster()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}