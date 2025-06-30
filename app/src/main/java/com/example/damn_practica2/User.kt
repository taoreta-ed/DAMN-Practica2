package com.example.damn_practica2

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity le dice a Room que esta clase es una tabla en la base de datos.
// tableName es opcional, pero es buena práctica para nombrar la tabla.
@Entity(tableName = "users")
data class User(
    // @PrimaryKey indica que esta propiedad es la clave primaria de la tabla.
    // autoGenerate = true permite que Room genere automáticamente un ID único.
    // El ' = 0' es CRUCIAL. Proporciona un valor por defecto para el ID
    // cuando creas un objeto User antes de insertarlo en la base de datos.
    // Room ignorará este 0 y generará su propio ID al insertar.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // <--- ¡Asegúrate de que ' = 0' esté presente aquí!
    val username: String,
    val passwordHash: String, // En una app real, esto sería el hash de la contraseña
    val role: String // "admin" o "user"
)
