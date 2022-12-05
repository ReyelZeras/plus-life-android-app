package com.example.pluslife

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pluslife.models.PostagemModel
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PostagemAdapter(
    private val postagens: List<PostagemModel>,
    private val idUsuario: String,
    private val onclick: (postagem: PostagemModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutDoCard = LayoutInflater.from(parent.context)
            .inflate(R.layout.res_card_item_feed, parent, false)

        return PostagemHolder(
            layoutDoCard,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostagemHolder).vincular(postagens[position])
    }

    override fun getItemCount(): Int {
        return postagens.size
    }

    inner class PostagemHolder(private val layoutDoCard: View): RecyclerView.ViewHolder(layoutDoCard) {
        fun vincular(postagem: PostagemModel) {
            val tvNome = layoutDoCard.findViewById<TextView>(R.id.tv_nome_usuario)
            val tvDataHora = layoutDoCard.findViewById<TextView>(R.id.tv_data_hora)
            val tvDescricao = layoutDoCard.findViewById<TextView>(R.id.tv_publicacao)
            val btnDeletar = layoutDoCard.findViewById<ImageView>(R.id.btn_excluir_publicacao)

            val dataHora = LocalDateTime.parse(postagem.dataHora)
            val data = dataHora.toLocalDate().toString()
            val hora = "${dataHora.hour}:${dataHora.minute}"

            tvDataHora.text = "$data  -  $hora"
            tvDescricao.text = postagem.descricao

            if (idUsuario.toInt() == postagem.usuarioEntity.id) {
                btnDeletar.isVisible = true
                btnDeletar.setOnClickListener { onclick(postagem) }
                tvNome.text = "Você"
            } else {
                btnDeletar.isVisible = false
                tvNome.text = postagem.usuarioEntity.nome
            }
        }
    }

}
