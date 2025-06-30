package com.example.damn_practica2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var textViewWelcome: TextView
    private lateinit var textViewUserRole: TextView
    private lateinit var textViewDashboardInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        textViewWelcome = findViewById(R.id.textViewWelcome)
        textViewUserRole = findViewById(R.id.textViewUserRole)
        textViewDashboardInfo = findViewById(R.id.textViewDashboardInfo)

        // Recuperar los datos pasados desde MainActivity
        val username = intent.getStringExtra("username")
        val userRole = intent.getStringExtra("userRole")

        // Actualizar los TextViews con la información del usuario
        textViewWelcome.text = "¡Bienvenido, ${username ?: "Usuario"}!"
        textViewUserRole.text = "Rol: ${userRole ?: "Desconocido"}"

        // Puedes añadir lógica aquí para mostrar diferentes elementos
        // o menús basados en el 'userRole'
        if (userRole == "admin") {
            textViewDashboardInfo.text = "Como Administrador, tienes acceso completo a las operaciones CRUD."
            // TODO: Mostrar opciones de administración
        } else {
            textViewDashboardInfo.text = "Como Usuario, puedes ver y actualizar tu perfil."
            // TODO: Mostrar opciones de perfil de usuario
        }
    }
}
