package com.dosan.sqlite_practice.ulitscrud

import android.provider.BaseColumns

class AlumnoContract {

    companion object {
        val VERSION = 1

        class Entrada : BaseColumns {
            companion object {
                val NOMBRE_TABLA = "alumnos"
                val COLUMNA_ID = "id"
                val COLUMNA_NOMBRE = "nombre"
                val COLUMNA_EDAD = "edad"
                val COLUMNA_TIPO = "tipo"
                val COLUMNA_MAYOREDAD = "mayor_edad"
            }
        }
    }
}