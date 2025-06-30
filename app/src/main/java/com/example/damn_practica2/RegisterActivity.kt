package com.example.damn_practica2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID // Importa UUID para generar IDs únicos

// Esta es la actividad para el registro de usuarios
class RegisterActivity : AppCompatActivity() {

    // Declarar los elementos de la UI
    private lateinit var editTextRegUsername: EditText
    private lateinit var editTextRegPassword: EditText
    private lateinit var editTextRegConfirmPassword: EditText
    private lateinit var buttonRegisterUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establece el layout para esta actividad usando el archivo activity_register.xml
        setContentView(R.layout.activity_register)

        // Inicializar los elementos de la UI encontrándolos por sus IDs
        editTextRegUsername = findViewById(R.id.editTextRegUsername)
        editTextRegPassword = findViewById(R.id.editTextRegPassword)
        editTextRegConfirmPassword = findViewById(R.id.editTextRegConfirmPassword)
        buttonRegisterUser = findViewById(R.id.buttonRegisterUser)

        // Configurar un OnClickListener para el botón de Registrar
        buttonRegisterUser.setOnClickListener {
            val username = editTextRegUsername.text.toString().trim() // .trim() para quitar espacios
            val password = editTextRegPassword.text.toString()
            val confirmPassword = editTextRegConfirmPassword.text.toString()

            // Validación de campos vacíos
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
            // Validación de longitud mínima de contraseña (opcional, pero buena práctica)
            else if (password.length < 4) { // Ejemplo: mínimo 4 caracteres
                Toast.makeText(this, "La contraseña debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show()
            }
            // Validación de coincidencia de contraseñas
            else if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
            // Validación de usuario ya existente (simulada)
            else if (UserDatabase.findUserByUsername(username) != null) {
                Toast.makeText(this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show()
            }
            else {
                // Generar un ID único para el nuevo usuario
                val newUserId = UUID.randomUUID().toString()
                // En una aplicación real, la contraseña se hashearía aquí antes de crear el objeto User
                // Por simplicidad para la simulación, usaremos la contraseña tal cual.
                val newUser = User(id = newUserId, username = username, passwordHash = password, role = "user")

                // Añadir el nuevo usuario a nuestra "base de datos" en memoria
                UserDatabase.addUser(newUser)

                Toast.makeText(this, "Usuario $username registrado exitosamente.", Toast.LENGTH_LONG).show()

                // Opcional: Limpiar los campos después del registro
                editTextRegUsername.setText("")
                editTextRegPassword.setText("")
                editTextRegConfirmPassword.setText("")

                // Volver a la pantalla de login después de un registro exitoso
                finish() // Cierra esta actividad y regresa a la anterior (MainActivity)
            }
        }
    }
}
