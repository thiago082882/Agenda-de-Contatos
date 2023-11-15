package com.example.room_agendadecontatos.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room_agendadecontatos.AppDataBase
import com.example.room_agendadecontatos.AtualizarUsuario
import com.example.room_agendadecontatos.DAO.UsuarioDAO
import com.example.room_agendadecontatos.databinding.ContatoItemBinding
import com.example.room_agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContatoAdapter
    (
    private val context : Context,
     private val listaUsuarios : MutableList<Usuario>
     ): RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context),parent,false)
       return ContatoViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.tvNome.text = listaUsuarios[position].nome
        holder.tvSobrenome.text = listaUsuarios[position].sobrenome
        holder.tvIdade.text = listaUsuarios[position].idade
        holder.tvCelular.text = listaUsuarios[position].telefone

        holder.btnAtualizar.setOnClickListener {

            val i = Intent(context,AtualizarUsuario::class.java)
            i.putExtra("nome",listaUsuarios[position].nome)
            i.putExtra("sobrenome",listaUsuarios[position].sobrenome)
            i.putExtra("idade",listaUsuarios[position].idade)
            i.putExtra("telefone",listaUsuarios[position].telefone)
            i.putExtra("uid",listaUsuarios[position].uid)
            context.startActivity(i)

        }

        holder.btnDeletar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val usuario = listaUsuarios[position]
                val usuarioDao : UsuarioDAO = AppDataBase.getInstance(context).usuarioDao()
                usuarioDao.delete(usuario.uid)
                listaUsuarios.remove(usuario)

                withContext(Dispatchers.Main){
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount() = listaUsuarios.size

    inner  class  ContatoViewHolder(binding : ContatoItemBinding) : RecyclerView.ViewHolder(binding.root) {


        val tvNome = binding.txtNome
        val tvSobrenome = binding.txtSobrenome
        val tvIdade = binding.txtIdade
        val tvCelular = binding.txtCelular
        val btnDeletar = binding.btDeletar
        val btnAtualizar = binding.btAtualizar
    }
}