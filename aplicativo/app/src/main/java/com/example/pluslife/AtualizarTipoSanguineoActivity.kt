package com.example.pluslife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityAtualizarTipoSanguineoBinding
import com.example.pluslife.databinding.ActivityCadastroBinding

class AtualizarTipoSanguineoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtualizarTipoSanguineoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarTipoSanguineoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botao1.setOnClickListener{}


        val spinner: Spinner = binding.tipoSanguineo
        ArrayAdapter.createFromResource(
            this,
            R.array.tipos_sanguineos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}