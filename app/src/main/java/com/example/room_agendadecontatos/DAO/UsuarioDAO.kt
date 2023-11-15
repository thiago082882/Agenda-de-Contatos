package com.example.room_agendadecontatos.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room_agendadecontatos.model.Usuario

@Dao
interface UsuarioDAO {

    @Insert
    fun inserir(listaUsuarios: MutableList<Usuario>)


    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC")
    fun get():MutableList<Usuario>

    @Query("UPDATE tabela_usuarios SET nome = :novoNome,sobrenome = :novoSobrenome, idade= :novaIdade, telefone = :novoTelefone  "
     + "WHERE uid = :uid")
    fun update(uid : Int,novoNome :String,novoSobrenome: String, novaIdade: String,novoTelefone:String)


    @Query("DELETE FROM tabela_usuarios WHERE uid = :id")
    fun delete(id:Int)
}