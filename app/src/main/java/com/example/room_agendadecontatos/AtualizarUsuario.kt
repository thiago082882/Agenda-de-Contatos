package com.example.room_agendadecontatos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.room_agendadecontatos.DAO.UsuarioDAO
import com.example.room_agendadecontatos.databinding.ActivityAtualizarUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AtualizarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarUsuarioBinding
    private lateinit var usuarioDAO: UsuarioDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtualizarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val nomeRecuperado = intent.extras?.getString("nome")
        val sobreNomeRecuperado = intent.extras?.getString("sobrenome")
        val idadeRecuperado = intent.extras?.getString("idade")
        val telefoneRecuperado = intent.extras?.getString("telefone")
        val uid = intent.extras!!.getInt("uid")

        binding.editNome.setText(nomeRecuperado)
        binding.editSobrenome.setText(sobreNomeRecuperado)
        binding.editIdade.setText(idadeRecuperado)
        binding.editCelular.setText(telefoneRecuperado)




        binding.btAtualizar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {


                val nome = binding.editNome.text.toString()
                val sobrenome = binding.editSobrenome.text.toString()
                val idade = binding.editIdade.text.toString()
                val telefone = binding.editCelular.text.toString()
                val mensagem: Boolean

                if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || telefone.isEmpty()) {

                    mensagem = false

                } else {
                    mensagem = true
                    atualizar(uid, nome, sobrenome, idade, telefone)
                }
                withContext(Dispatchers.Main) {
                    if (mensagem) {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Sucesso ao atualizar usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@AtualizarUsuario,
                            "Preencha todos campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun atualizar(
        uid: Int,
        nome: String,
        sobrenome: String,
        idade: String,
        telefone: String
    ) {

        usuarioDAO = AppDataBase.getInstance(this).usuarioDao()
        usuarioDAO.update(uid, nome, sobrenome, idade, telefone)
    }


}