package com.example.room_agendadecontatos

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_agendadecontatos.DAO.UsuarioDAO
import com.example.room_agendadecontatos.adapter.ContatoAdapter
import com.example.room_agendadecontatos.databinding.ActivityMainBinding
import com.example.room_agendadecontatos.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private  lateinit var  usuarioDao : UsuarioDAO
    private lateinit var  contatoAdapter : ContatoAdapter
    private val _listaUsuario = MutableLiveData<MutableList<Usuario>>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btCadastrar.setOnClickListener {
            val navegarTelaCadatro = Intent(this,CadastrarUsuario::class.java)
            startActivity(navegarTelaCadatro)

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            getContatos()
            withContext(Dispatchers.Main){

                _listaUsuario.observe(this@MainActivity){ listaUsuario ->

                    val recyclerViewContatos = binding.recyclerViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContatos.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity,listaUsuario)
                    recyclerViewContatos.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()
                }

            }
        }
    }
    private fun getContatos(){

        usuarioDao = AppDataBase.getInstance(this).usuarioDao()
        val listaUsuarios : MutableList<Usuario> = usuarioDao.get()
        _listaUsuario.postValue(listaUsuarios)

    }
}