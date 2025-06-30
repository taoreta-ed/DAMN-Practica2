package com.example.damn_practica2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Para usar coroutines con el ciclo de vida de la actividad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID // Importa UUID para generar IDs únicos

// Esta es la actividad para el registro de usuarios
class RegisterActivity : AppCompatActivity() {

    // Declarar los elementos de la UI
    private lateinit var editTextRegUsername: EditText
    private lateinit var editTextRegPassword: EditText
    private lateinit var editTextRegConfirmPassword: EditText
    private lateinit var spinnerRole: Spinner // Nuevo Spinner para el rol
    private lateinit var buttonRegisterUser: Button

    private lateinit var userDao: UserDao // Instancia del DAO de Room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establece el layout para esta actividad usando el archivo activity_register.xml
        setContentView(R.layout.activity_register)

        // Inicializar la base de datos Room y el DAO
        val database = AppDatabase.getDatabase(this, lifecycleScope)
        userDao = database.userDao()

        editTextRegUsername = findViewById(R.id.editTextRegUsername)
        editTextRegPassword = findViewById(R.id.editTextRegPassword)
        editTextRegConfirmPassword = findViewById(R.id.editTextRegConfirmPassword)
        spinnerRole = findViewById(R.id.spinnerRole) // Inicializar el Spinner
        buttonRegisterUser = findViewById(R.id.buttonRegisterUser)

        // Configurar el Spinner con las opciones de rol
        val roles = arrayOf("user", "admin") // Opciones de rol
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = adapter

        buttonRegisterUser.setOnClickListener {
            val username = editTextRegUsername.text.toString().trim()
            val password = editTextRegPassword.text.toString()
            val confirmPassword = editTextRegConfirmPassword.text.toString()
            val selectedRole = spinnerRole.selectedItem.toString() // Obtener el rol seleccionado

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
            else {
                // Usar coroutines para operaciones de base de datos asíncronas
                lifecycleScope.launch(Dispatchers.IO) {
                    val existingUser = userDao.getUserByUsername(username)
                    withContext(Dispatchers.Main) {
                        if (existingUser != null) {
                            Toast.makeText(this@RegisterActivity, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show()
                        } else {
                            // Crear un nuevo usuario con el rol seleccionado
                            // Nota: El ID es autoGenerate en la entidad User de Room
                            val newUser = User(username = username, passwordHash = password, role = selectedRole)
                            userDao.insertUser(newUser) // Insertar en Room

                            Toast.makeText(this@RegisterActivity, "Usuario $username registrado exitosamente como $selectedRole.", Toast.LENGTH_LONG).show()

                            editTextRegUsername.setText("")
                            editTextRegPassword.setText("")
                            editTextRegConfirmPassword.setText("")
                            finish() // Volver a la pantalla de login
                        }
                    }
                }
            }
        }
    }
}
