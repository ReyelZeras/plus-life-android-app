package com.example.pluslife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pluslife.databinding.FragmentNavbarBinding

class NavbarFragment : Fragment(R.layout.fragment_navbar) {

    private lateinit var binding : FragmentNavbarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavbarBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.navHome.setOnClickListener { trocarTela(HomeActivity()) }
        binding.navPerfil.setOnClickListener { (activity as MainActivity).telaPerfil() }
        //binding.navPontos.setOnClickListener { trocarTela(ComoDoarFragment()) }

    }

    fun telaPerfil() {
        val novaTela = Intent(
            activity,
            PerfilActivity::class.java
        )
        startActivity(novaTela)
    }

}