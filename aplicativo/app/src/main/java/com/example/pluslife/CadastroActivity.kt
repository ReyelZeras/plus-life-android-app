import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pluslife.databinding.ActivityCadastroBinding
import com.example.pluslife.databinding.ActivityMainBinding
import com.example.pluslife.models.CadastroDoadorRequest
import com.example.pluslife.models.CadastroEnderecoRequest
import com.example.pluslife.models.LoginRequest
import com.example.pluslife.models.LoginResponse
import com.example.pluslife.rest.Rest
import com.example.pluslife.services.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener{
            tryCadastro()}
    }

    private fun tryCadastro(){
        val nome = binding.etNome.text.toString()
        val email = binding.etEmail.text.toString()
        val senha = binding.etSenha.text.toString()
        val tipoSanguineo = binding.tipoSanguineo.text.toString()
        val nascimento = binding.etNascimento.text.toString()

        val body = CadastroDoadorRequest( nome, email, senha, nascimento, tipoSanguineo)

        val request = Rest.getInstance().create(Usuario::class.java)

        request.cadastroDoador(body).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                tryCadastroEndereco(request)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun tryCadastroEndereco(request: Usuario) {
        val email = binding.etEmail.text.toString()
        val cidade = binding.etCidade.text.toString()
        val estado = binding.etEstado.text.toString()
        val rua = binding.etRua.text.toString()
        val numero = binding.etNumero.text.toString().toInt()
        val bairro = binding.etBairro.text.toString()

        val body = CadastroEnderecoRequest(email, bairro, rua, numero, cidade, estado)

        request.cadastroEndereco(body).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}