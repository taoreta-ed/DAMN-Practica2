package com.example.damn_practica2

// Objeto singleton para simular una base de datos de usuarios en memoria.
// En una aplicación real, esto interactuaría con un backend y una base de datos persistente.
object UserDatabase {
    // Lista mutable para almacenar los usuarios registrados.
    // Usamos 'private' para encapsular el acceso y 'val' porque la referencia a la lista no cambiará.
    private val users = mutableListOf<User>()

    // Función para añadir un nuevo usuario a la "base de datos".
    fun addUser(user: User) {
        users.add(user)
    }

    // Función para encontrar un usuario por su nombre de usuario.
    fun findUserByUsername(username: String): User? {
        // 'find' es una función de extensión de colecciones que devuelve el primer elemento
        // que coincide con el predicado dado, o 'null' si no se encuentra ninguno.
        return users.find { it.username == username }
    }

    // Función para obtener todos los usuarios (útil para depuración o si un admin los listara).
    fun getAllUsers(): List<User> {
        return users.toList() // Retorna una copia inmutable para evitar modificaciones externas
    }
}
