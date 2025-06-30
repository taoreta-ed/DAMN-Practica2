package com.example.damn_practica2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
            } else {
                // Buscar el usuario en nuestra base de datos simulada
                val user = UserDatabase.findUserByUsername(username)

                // Verificar si el usuario existe y si la contraseña coincide
                if (user != null && user.passwordHash == password) {
                    Toast.makeText(this, "¡Bienvenido, ${user.username}!", Toast.LENGTH_LONG).show()

                    // Navegación al DashboardActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    // Pasar el nombre de usuario y el rol a la DashboardActivity
                    intent.putExtra("username", user.username)
                    intent.putExtra("userRole", user.role)
                    startActivity(intent)
                    finish() // Cierra MainActivity para que el usuario no pueda volver con el botón atrás
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
