package com.dosan.sqlite_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
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

        var veriticaMayorEdad = 0


        val btn_add = findViewById<Button>(R.id.btnRegistrar)

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