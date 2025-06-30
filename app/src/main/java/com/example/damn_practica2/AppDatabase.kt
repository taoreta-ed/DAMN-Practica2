package com.example.damn_practica2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

// @Database le dice a Room que esta es la clase de la base de datos.
// entities: Lista de todas las entidades (tablas) en la base de datos.
// version: Versión de la base de datos. Incrementa si cambias el esquema.
// exportSchema: Si exportar el esquema a un archivo JSON (recomendado para control de versiones).
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Define un método abstracto para obtener el DAO. Room lo implementará.
    abstract fun userDao(): UserDao

    companion object {
        // @Volatile asegura que el valor de INSTANCE sea siempre el más actualizado
        // y que todos los hilos lo vean correctamente.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método para obtener la instancia de la base de datos (Singleton).
        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // Si la instancia es null, crea una nueva.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "damn_practica2_database" // Nombre del archivo de la base de datos
                )
                    // Callback para pre-poblar la base de datos (ej. con un admin)
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Clase de callback para pre-poblar la base de datos.
        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        val userDao = database.userDao()
                        // Pre-poblar con un usuario administrador
                        userDao.insertUser(User(username = "admin", passwordHash = "admin123", role = "admin"))
                    }
                }
            }
        }
    }
}
