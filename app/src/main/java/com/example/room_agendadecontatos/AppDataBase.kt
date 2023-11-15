package com.example.room_agendadecontatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room_agendadecontatos.DAO.UsuarioDAO
import com.example.room_agendadecontatos.model.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO

    companion object {
        private const val DATABASE_NOME ="DB_USUARIOS"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DATABASE_NOME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}