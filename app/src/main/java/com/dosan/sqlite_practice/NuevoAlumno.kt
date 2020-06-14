package com.dosan.sqlite_practice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dosan.sqlite_practice.ulitscrud.Alumno
import com.dosan.sqlite_practice.ulitscrud.AlumnoCrud

class NuevoAlumno : AppCompatActivity() {

    var crud: AlumnoCrud? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_alumno)

        //val id = findViewById<EditText>(R.id.edId)
        val nombre = findViewById<EditText>(R.id.edNombre)
        val edad = findViewById<EditText>(R.id.edEdad)
        val tipo = findViewById<EditText>(R.id.edTipo)
        val mayorEdad = findViewById<CheckBox>(R.id.cbMayorEdad)
        val id_alumno = findViewById<TextView>(R.id.id)

        var veriticaMayorEdad = 0

        val btn_add = findViewById<Button>(R.id.btnRegistrar)

        //val id = intent.getStringExtra("ID")
        if (intent.hasExtra("ID")) {
            var crud: AlumnoCrud? = null
            crud = AlumnoCrud(this)
            val id = intent.getStringExtra("ID")
            //Log.d("id", "el id es : $id")

            var alumno = crud.getAlumno(id)

            nombre.setText(alumno.nombre, TextView.BufferType.EDITABLE)
            edad.setText(alumno.edad.toString(), TextView.BufferType.EDITABLE)
            tipo.setText(alumno.tipo, TextView.BufferType.EDITABLE)
            id_alumno.setText(alumno.id.toString())
            btn_add.text = "Actualizar Registro"
            btn_add.setOnClickListener {
                crud?.updateAlumno(
                    Alumno(
                        id_alumno.text.toString().toInt(),
                        nombre.text.toString(),
                        edad.text.toString().toInt(),
                        tipo.text.toString(),
                        veriticaMayorEdad
                    )
                )
                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            btn_add.text = "Nuevo registro"
            crud = AlumnoCrud(this)
            btn_add.setOnClickListener {
                if (mayorEdad.isChecked) {
                    veriticaMayorEdad = 1
                } else {
                    veriticaMayorEdad = 0
                }
                crud?.newAlumno(
                    Alumno(
                        0,
                        nombre.text.toString(),
                        edad.text.toString().toInt(),
                        tipo.text.toString(),
                        veriticaMayorEdad
                    )
                )
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }
}