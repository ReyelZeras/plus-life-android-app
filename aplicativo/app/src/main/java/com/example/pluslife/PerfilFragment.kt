package com.example.pluslife

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pluslife.databinding.FragmentPerfilBinding

class PerfilFragment: Fragment() {

    private lateinit var binding : FragmentPerfilBinding
//    private val mainActivity = (activity as MainActivity)
//    private val prefs = mainActivity.getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //verificarLogin()

        //al doador = buscarDados()

//        binding.btnVoltar.setOnClickListener {
//            mainActivity.trocarTela(HomeFragment())
//        }
//
//        binding.edtNome.setOnClickListener {
//            mainActivity.trocarTela(AtualizarNomeFragment(doador))
//        }
//
//        binding.edtEmail.setOnClickListener {
//            mainActivity.trocarTela(AtualizarEmailFragment())
//        }
//
//        binding.edtSenha.setOnClickListener {
//            mainActivity.trocarTela(AtualizarSenhaFragment())
//        }
//
//        binding.edtNome.setOnClickListener {
//            mainActivity.trocarTela(AtualizarEnderecoFragment())
//        }

    }

//    private fun verificarLogin() {
//        val isLogado = prefs.getBoolean(USUARIO_LOGADO.toString(), false)
//
//        if (!isLogado) {
//            mainActivity.telaLogin()
//        }
//    }

//    private fun buscarDados(): DoadorModel {
//       return DoadorModel(
//           id = prefs.getString(USUARIO_ID.toString(), ""),
//           nome = prefs.getString(USUARIO_NOME.toString(), ""),
//           email = prefs.getString(USUARIO_EMAIL.toString(), ""),
//           nascimento = LocalDate.parse(prefs.getString(USUARIO_NASCIMENTO.toString(), "")),
//           tipoSanguineo = prefs.getString(USUARIO_TIPO_SANGUINEO.toString(), "")
//       )
//    }
}