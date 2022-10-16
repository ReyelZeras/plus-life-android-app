package com.example.pluslife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pluslife.databinding.ActivityAtualizarEnderecoBinding
import com.example.pluslife.databinding.ActivityCadastroBinding

class AtualizarEnderecoActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAtualizarEnderecoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botao1.setOnClickListener{}

        val spinnerEstado : Spinner = binding.etEstado
        ArrayAdapter.createFromResource(
            this,
            R.array.estados,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEstado.adapter = adapter
        }


    }
}