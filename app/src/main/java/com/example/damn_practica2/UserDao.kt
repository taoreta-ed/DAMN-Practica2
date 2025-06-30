package com.example.damn_practica2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// @Dao le dice a Room que esta interfaz es un Data Access Object.
@Dao
interface UserDao {
    // @Insert define un método para insertar un nuevo usuario en la tabla.
    // OnConflictStrategy.REPLACE significa que si el usuario ya existe (por PrimaryKey),
    // se reemplazará. Para nuestro caso, el ID es autoGenerate, así que no debería haber conflicto por ID.
    @Insert(onConflict = OnConflictStrategy.IGNORE) // Cambiado a IGNORE para evitar reemplazar si el nombre de usuario fuera la clave primaria
    suspend fun insertUser(user: User): Long // suspend para usar coroutines, Long es el rowId insertado

    // @Query define una consulta SQL para buscar un usuario por su nombre de usuario.
    // Retorna un User o null si no se encuentra.
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}
