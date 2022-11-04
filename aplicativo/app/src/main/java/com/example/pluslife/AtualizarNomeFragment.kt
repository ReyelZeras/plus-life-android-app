//package com.example.pluslife
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.example.pluslife.databinding.FragmentAtualizarNomeBinding
//import com.example.pluslife.models.DoadorModel
//import com.example.pluslife.models.enum.DadosSharedSecret
//import com.example.pluslife.services.AtualizarService
//
//class AtualizarNomeFragment(var doador: DoadorModel) : Fragment() {
//
//    lateinit var binding: FragmentAtualizarNomeBinding
//    private val atualizarService = AtualizarService()
//    private val mainActivity = (activity as MainActivity)
//    private val prefs = mainActivity.getSharedPreferences("DADOS", AppCompatActivity.MODE_PRIVATE)
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentAtualizarNomeBinding.inflate(inflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnVoltar.setOnClickListener {
//            mainActivity.trocarTela(PerfilFragment())
//        }
//
//        binding.btnSalvar.setOnClickListener {
//            doador.nome = binding.etNome.text.toString()
//            when (atualizarService.tryAtualizarDoador(doador)) {
//                0 -> { binding.tvMensagem.text = "Ocorreu um erro ao atualizar seu nome" }
//                200 -> {
//                    binding.tvMensagem.text = "Nome atualizado com sucesso"
//                    val editor = prefs.edit()
//                    editor.putString(DadosSharedSecret.USUARIO_NOME.toString(), binding.etNome.text.toString())
//                    editor.apply()
//                }
//            }
//        }
//    }
//
//}