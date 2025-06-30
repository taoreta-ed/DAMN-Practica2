package com.example.damn_practica2

import android.os.Bundle
import android.view.View // Importa la clase View para controlar la visibilidad
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var textViewWelcome: TextView
    private lateinit var textViewUserRole: TextView
    private lateinit var textViewDashboardInfo: TextView
    private lateinit var textViewAdminMessage: TextView // Nuevo TextView para admin
    private lateinit var textViewUserMessage: TextView  // Nuevo TextView para usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        textViewWelcome = findViewById(R.id.textViewWelcome)
        textViewUserRole = findViewById(R.id.textViewUserRole)
        textViewDashboardInfo = findViewById(R.id.textViewDashboardInfo)
        textViewAdminMessage = findViewById(R.id.textViewAdminMessage) // Inicializar
        textViewUserMessage = findViewById(R.id.textViewUserMessage)  // Inicializar

        // Recuperar los datos pasados desde MainActivity
        val username = intent.getStringExtra("username")
        val userRole = intent.getStringExtra("userRole")

        // Actualizar los TextViews con la información del usuario
        textViewWelcome.text = "¡Bienvenido, ${username ?: "Usuario"}!"
        textViewUserRole.text = "Rol: ${userRole ?: "Desconocido"}"

        // Lógica para mostrar diferentes elementos basados en el 'userRole'
        if (userRole == "admin") {
            textViewDashboardInfo.text = "Como Administrador, tienes acceso completo a las operaciones CRUD."
            textViewAdminMessage.visibility = View.VISIBLE // Hacer visible el mensaje de admin
            textViewUserMessage.visibility = View.GONE    // Asegurarse de que el mensaje de usuario esté oculto
        } else { // Asumimos "user" si no es "admin"
            textViewDashboardInfo.text = "Como Usuario, puedes ver y actualizar tu perfil."
            textViewAdminMessage.visibility = View.GONE    // Asegurarse de que el mensaje de admin esté oculto
            textViewUserMessage.visibility = View.VISIBLE // Hacer visible el mensaje de usuario
        }
    }
}
