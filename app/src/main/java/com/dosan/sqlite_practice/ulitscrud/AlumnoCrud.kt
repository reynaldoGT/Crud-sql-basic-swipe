package com.dosan.sqlite_practice.ulitscrud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class AlumnoCrud(context: Context) {
    private var helper: DatabaseHelper? = null

    init {
        helper = DatabaseHelper(context)
    }

    fun newAlumno(item: Alumno) {
        //Funcion que nso permite escribir en una base de datos
        val db: SQLiteDatabase = helper?.writableDatabase!!
        //Mapeo con los datos a insertar
        val values = ContentValues()

        values.put(AlumnoContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombre)
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_EDAD, item.edad)
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_TIPO, item.tipo)
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_MAYOREDAD, item.mayor_edad)

        //Insertamos una fila en la tabla
        val newRowId = db.insert(AlumnoContract.Companion.Entrada.NOMBRE_TABLA, null, values)
        db.close()

    }

    fun getAlumnos(): ArrayList<Alumno> {
        var items: ArrayList<Alumno> = ArrayList()

        // Abrir la cbase de datos en mode de lectura
        var db: SQLiteDatabase = helper?.readableDatabase!!

        //Especificar columnas que quiero consultar
        var columnas = arrayOf(
            AlumnoContract.Companion.Entrada.COLUMNA_ID,
            AlumnoContract.Companion.Entrada.COLUMNA_NOMBRE,
            AlumnoContract.Companion.Entrada.COLUMNA_EDAD,
            AlumnoContract.Companion.Entrada.COLUMNA_TIPO,
            AlumnoContract.Companion.Entrada.COLUMNA_MAYOREDAD
        )

        //Crear un cros para recorre la tabla
        var c: Cursor = db.query(
            AlumnoContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            null,
            null,
            null,
            null,
            null
        )
        //Log.d("consulta",c.)
        // Hacer un recorrido del cursor en la tabla
        while (c.moveToNext()) {
            items.add(
                Alumno(
                    c.getInt((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_ID))),
                    c.getString((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_NOMBRE))),
                    c.getInt((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_EDAD))),
                    //c.getString((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_TIPO))),
                    c.getString((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_TIPO))),
                    c.getInt((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_MAYOREDAD)))
                )
            )
        }
        //Cerrar DB
        db.close()
        return items
    }

    fun getAlumno(id: String): Alumno {
        var item: Alumno? = null
        // Abrir la cbase de datos en mode de lectura
        var db: SQLiteDatabase = helper?.readableDatabase!!

        //Especifiar columnas que quiero consultar
        val values = ContentValues()
        var columnas = arrayOf(
            AlumnoContract.Companion.Entrada.COLUMNA_ID,
            AlumnoContract.Companion.Entrada.COLUMNA_NOMBRE,
            AlumnoContract.Companion.Entrada.COLUMNA_EDAD,
            AlumnoContract.Companion.Entrada.COLUMNA_TIPO,
            AlumnoContract.Companion.Entrada.COLUMNA_MAYOREDAD
        )
        //Crear un cros para recorre la tabla
        var c: Cursor = db.query(
            AlumnoContract.Companion.Entrada.NOMBRE_TABLA,
            columnas,
            " id = ?",
            arrayOf(id),
            null,
            null,
            null
        )
        // Hacer un recorrido del cursor en la tabla
        while (c.moveToNext()) {
            item = Alumno(

                c.getInt((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_ID))),
                c.getString((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_NOMBRE))),
                c.getInt((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_EDAD))),
                c.getString((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_TIPO))),
                c.getInt((c.getColumnIndexOrThrow(AlumnoContract.Companion.Entrada.COLUMNA_MAYOREDAD)))
            )

        }
        //Cerrar DB
        db.close()
        return item!!
    }

    fun updateAlumno(item: Alumno) {
        var db: SQLiteDatabase = helper?.readableDatabase!!

        //Especifiar columnas que quiero consultar
        val values = ContentValues()
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_ID, item.id)
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_NOMBRE, item.nombre)
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_EDAD, item.edad)
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_MAYOREDAD, item.mayor_edad)
        values.put(AlumnoContract.Companion.Entrada.COLUMNA_TIPO, item.tipo)
        db.update(
            AlumnoContract.Companion.Entrada.NOMBRE_TABLA,
            values,
            " id = ?",
            arrayOf(item.id.toString())
        )
        db.close()
    }

    fun deleteAlumno(id: Int) {
        val db: SQLiteDatabase = helper?.writableDatabase!!

        db.delete(
            AlumnoContract.Companion.Entrada.NOMBRE_TABLA,
            " id = ?",
            arrayOf(id.toString())
        )
        db.close()
    }
}