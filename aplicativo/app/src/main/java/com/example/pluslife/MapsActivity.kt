package com.example.pluslife

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.pluslife.models.enum.EnderecoSharedSecret
import com.example.pluslife.models.enum.UsuarioSharedSecret
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
    private lateinit var enderecoUsuario: UsuarioEnderecoRequest
    private lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("DADOS", MODE_PRIVATE)


        pontos = intent.getSerializableExtra("pontos") as List<BancoDeSangueEnderecoModel>
        enderecoUsuario = intent.getSerializableExtra("enderecoUsuario") as UsuarioEnderecoRequest
        binding.btnVoltar.setOnClickListener {
            val novaTela = Intent(
                this,
                BancosProximosActivity::class.java
            )
            if (verificarSeEnderecoAtualIgualCadastro()){
                novaTela.putExtra("isNovoEndereco", false)
            } else {
                novaTela.putExtra("isNovoEndereco", true)
            }
            startActivity(novaTela)
        }

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
        tryUserCoordenates(enderecoUsuario, googleMap)
    }



    private fun tryUserCoordenates(enderecoUsuario: UsuarioEnderecoRequest, googleMap: GoogleMap) {
        val request = Rest.getInstance().create(Usuario::class.java)

        request.coordenadas(enderecoUsuario)
            .enqueue(object : Callback<GeocodeResponse> {
                override fun onResponse(
                    call: Call<GeocodeResponse>,
                    response: Response<GeocodeResponse>
                ) {
                    if (response.code() == 200) {
                        configurarMapa(googleMap, response.body()!!)
                    }
                }

                override fun onFailure(call: Call<GeocodeResponse>, t: Throwable) {
                    telaErro(t.message.toString())

                }
            })
    }

    private fun configurarMapa(googleMap: GoogleMap, userGeocode: GeocodeResponse){
        mMap = googleMap
        val lat = userGeocode.results?.get(0)?.geometry?.location?.lat!!
        val lng = userGeocode.results?.get(0)?.geometry?.location?.lng!!
        val userCoordenates = LatLng(lat.toDouble(), lng.toDouble())
        mMap.addMarker(MarkerOptions().position(userCoordenates).title("Sua localização"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCoordenates, 12.5.toFloat()))

        // Add marker for donation points
        for (ponto in pontos) {
            val location = LatLng(ponto.latitude.toDouble(), ponto.longitude.toDouble())
            print("location: $location")
            mMap.addMarker(MarkerOptions().position(location).title(ponto.nome))
        }

    }
    fun trocarTela(activity: Activity){
        val novaTela = Intent(
            this,
            activity::class.java
        )
        startActivity(novaTela)
    }


    fun telaErro(mensagem: String){
        val novaTela = Intent(
            this,
            ErroActivity::class.java
        )
        novaTela.putExtra("tela", "BANCOS_PROXIMOS")
        novaTela.putExtra("mensagem", mensagem)
        startActivity(novaTela)
    }

    // verifica se o usuario esta utilizando o endereco cadastrado ou nao
    private fun verificarSeEnderecoAtualIgualCadastro(): Boolean {
        val ruaCadastro = prefs.getString(UsuarioSharedSecret.USUARIO_ENDERECO_RUA.toString(), "")
        val numeroCadastro = prefs.getInt(UsuarioSharedSecret.USUARIO_ENDERECO_NUMERO.toString(), 0)
        val ruaAtual = prefs.getString(EnderecoSharedSecret.RUA.toString(), "")
        val numeroAtual = prefs.getInt(EnderecoSharedSecret.NUMERO.toString(), 0)

        if (ruaCadastro == ruaAtual && numeroCadastro == numeroAtual) {
            return true
        }

        return false
    }

}