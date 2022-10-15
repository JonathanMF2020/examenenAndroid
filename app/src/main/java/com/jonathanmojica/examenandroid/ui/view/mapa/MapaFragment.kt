package com.jonathanmojica.examenandroid.ui.view.mapa

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.jonathanmojica.examenandroid.R
import com.jonathanmojica.examenandroid.databinding.FragmentMapaBinding
import com.jonathanmojica.examenandroid.model.data.DataRequest
import com.jonathanmojica.examenandroid.service.ApiRepository
import com.jonathanmojica.examenandroid.ui.view.login.LoginFactory
import com.jonathanmojica.examenandroid.ui.view.login.LoginViewModel
import com.jonathanmojica.examenandroid.utils.Settings
import com.jonathanmojica.examenandroid.utils.Status
import java.lang.Exception


class MapaFragment : Fragment() {


    private lateinit var binding: FragmentMapaBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: MapaViewModel
    private val repository = Settings.getRetrofit()


    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapaBinding.inflate(inflater, container, false)
        initViewModel()
        listener()
        responses()
        mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        permisosLocation()
        return binding.root
    }

    /**
     * Inicializa los viewmodel
     */
    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            MapaFactory(ApiRepository(repository))
        ).get(MapaViewModel::class.java)
    }

    /**
     * Obtiene las respuestas
     */
    fun responses() {
        viewModel.getData.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(),
                            "Se ha enviado tu ubicación exitosamente",
                            Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context,
                            "Ha ocurrido un error mientras se intenta iniciar sesión",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    /**
     * Pregunta por permisos de localización
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.N)
    fun permisosLocation(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    mapFragment.getMapAsync { googleMap ->
                        var location = LatLng(0.0, 0.0)
                        fusedLocationClient.lastLocation.addOnSuccessListener {
                            Log.d("MapaFragment", "Entro: ${it.latitude} ${it.longitude}")
                            location = LatLng(it.latitude, it.longitude)
                            googleMap.addMarker(MarkerOptions()
                                .position(location)
                                .title("Te encuentras aqui"))
                            googleMap.uiSettings.isZoomControlsEnabled = true

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))
                            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                        }
                    }
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    mapFragment.getMapAsync { googleMap ->
                        var location = LatLng(0.0, 0.0)
                        fusedLocationClient.lastLocation.addOnSuccessListener {
                            Log.d("MapaFragment", "Entro: ${it.latitude} ${it.longitude}")
                            location = LatLng(it.latitude, it.longitude)
                            googleMap.addMarker(MarkerOptions()
                                .position(location)
                                .title("Te encuentras aqui"))
                            googleMap.uiSettings.isZoomControlsEnabled = true

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))
                            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                        }
                    }
                } else -> {
                    Toast.makeText(context, "Debes activar la localización", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack(R.id.loginFragment,false)
                }
            }
        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))


    }

    /**
     * Escucha los eventos del boton
     */
    @SuppressLint("MissingPermission")
    fun listener(){
        binding.btnEnviar.setOnClickListener{
            fusedLocationClient.lastLocation.addOnSuccessListener {
                Log.d("MapaFragment","Entro: ${it.latitude} ${it.longitude}")
                try{
                    var sendRequest = DataRequest(it.latitude.toString(),it.longitude.toString())
                    viewModel.sendData(sendRequest)
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        }

        binding.btnCerrar.setOnClickListener{
            Log.d("MapaFragment","Holaaa")
            val sharedPref = activity?.getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE)
            sharedPref!!.edit().putBoolean("Logueado",false).apply()
            findNavController().popBackStack(R.id.loginFragment,false)
        }
    }
}