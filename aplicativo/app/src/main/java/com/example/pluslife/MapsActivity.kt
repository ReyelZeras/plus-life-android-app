package com.example.pluslife

import android.content.Context
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.pluslife.databinding.ActivityMapsBinding
import com.example.pluslife.models.BancoDeSangueEnderecoModel
import com.example.pluslife.models.GeocodeResponse
import com.example.pluslife.models.Location
import com.example.pluslife.models.UsuarioEnderecoRequest
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Banco
import com.example.pluslife.services.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var pontos: List<BancoDeSangueEnderecoModel>
    private lateinit var userGeocode: GeocodeResponse
    private lateinit var enderecoUsuario: UsuarioEnderecoRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pontos = intent.getSerializableExtra("pontos") as List<BancoDeSangueEnderecoModel>
        enderecoUsuario = intent.getSerializableExtra("enderecoUsuario") as UsuarioEnderecoRequest

        tryUserCoordenates(enderecoUsuario)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lat = userGeocode.results?.get(0)?.geometry?.location?.lat!!
        val lng = userGeocode.results?.get(0)?.geometry?.location?.lng!!
        val userCoordenates = LatLng(lat.toDouble(), lng.toDouble())
        mMap.addMarker(MarkerOptions().position(userCoordenates).title("Sua localização"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userCoordenates))

        // Add marker for donation points
        for (ponto in pontos) {
            val location = LatLng(ponto.latitude.toDouble(), ponto.longitude.toDouble())
            mMap.addMarker(MarkerOptions().position(location).title(ponto.nome))
        }
    }

    private fun tryUserCoordenates(enderecoUsuario: UsuarioEnderecoRequest) {
        val request = Rest.getInstance().create(Usuario::class.java)

        request.coordenadas(enderecoUsuario)
            .enqueue(object : Callback<GeocodeResponse> {
                override fun onResponse(
                    call: Call<GeocodeResponse>,
                    response: Response<GeocodeResponse>
                ) {
                    if (response.code() == 200) {
                        userGeocode = response.body()!!
                    }
                }

                override fun onFailure(call: Call<GeocodeResponse>, t: Throwable) {
                    println(
                        "--------- \n ---------- \n " +
                        "erro na requisição de coordenadas do usuario " +
                        "\n -------- \n ---------"
                    )
                }
            })
    }
}