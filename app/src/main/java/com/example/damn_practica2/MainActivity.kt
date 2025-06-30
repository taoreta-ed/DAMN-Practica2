package com.example.damn_practica2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Para usar coroutines con el ciclo de vida de la actividad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    private lateinit var userDao: UserDao // Instancia del DAO de Room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar la base de datos Room y el DAO
        val database = AppDatabase.getDatabase(this, lifecycleScope)
        userDao = database.userDao()

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
                // Usar coroutines para operaciones de base de datos asíncronas
                lifecycleScope.launch(Dispatchers.IO) {
                    val user = userDao.getUserByUsername(username) // Buscar usuario en Room
                    withContext(Dispatchers.Main) {
                        if (user != null && user.passwordHash == password) {
                            Toast.makeText(this@MainActivity, "¡Bienvenido, ${user.username}!", Toast.LENGTH_LONG).show()

                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            intent.putExtra("username", user.username)
                            intent.putExtra("userRole", user.role)
                            startActivity(intent)
                            finish() // Cierra MainActivity para que el usuario no pueda volver con el botón atrás
                        } else {
                            Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
