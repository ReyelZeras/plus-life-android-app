package com.example.pluslife

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pluslife.models.BancoDeSangueEnderecoModel

class BancoDeSangueAdapter(
    private val pontos: List<BancoDeSangueEnderecoModel>,
    private val onclick: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutDoCard = LayoutInflater.from(parent.context)
            .inflate(R.layout.res_card_item, parent, false)

        return BancoDeSangueHolder(
            layoutDoCard,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BancoDeSangueHolder).vincular(pontos[position])
    }

    override fun getItemCount(): Int {
        return pontos.size
    }

    inner class BancoDeSangueHolder(private val layoutDoCard: View): RecyclerView.ViewHolder(layoutDoCard) {
        fun vincular(banco: BancoDeSangueEnderecoModel) {
            val tvNome = layoutDoCard.findViewById<TextView>(R.id.tv_nome_banco)
            val tvEndereco = layoutDoCard.findViewById<TextView>(R.id.tv_endereco_banco)
            val tvTelefone = layoutDoCard.findViewById<TextView>(R.id.tv_telefone_banco)
            val tvEmail = layoutDoCard.findViewById<TextView>(R.id.tv_email_banco)
            val btnCard = layoutDoCard.findViewById<LinearLayout>(R.id.btn_card)

            tvNome.text = banco.nome
            tvEndereco.text = "${banco.rua}, ${banco.numero}, ${banco.bairro}, ${banco.cidade} - ${banco.estado}"
            tvTelefone.text = banco.telefone
            tvEmail.text = banco.emailContato
            btnCard.setOnClickListener { onclick }
        }
    }

}