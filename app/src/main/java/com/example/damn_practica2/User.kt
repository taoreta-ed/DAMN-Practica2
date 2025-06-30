package com.example.damn_practica2

// Clase de datos (data class) para representar un usuario en la aplicación.
// Una data class es ideal para almacenar datos y automáticamente genera
// métodos útiles como equals(), hashCode(), toString(), y copy().
data class User(
    // Identificador único para el usuario. En una base de datos real,
    // esto podría ser autogenerado.
    val id: String,
    // Nombre de usuario, usado para el login y como identificador público.
    val username: String,
    // La contraseña del usuario. NOTA: En una aplicación real, nunca
    // almacenarías la contraseña en texto plano aquí ni la enviarías así.
    // Se usaría un hash de la contraseña (por ejemplo, SHA-256) y se
    // enviaría de forma segura (HTTPS).
    val passwordHash: String, // Representa la contraseña hasheada
    // Rol del usuario (por ejemplo, "admin", "user"). Esto es crucial
    // para la lógica de permisos de la práctica.
    val role: String // Puede ser "admin" o "user"
)
