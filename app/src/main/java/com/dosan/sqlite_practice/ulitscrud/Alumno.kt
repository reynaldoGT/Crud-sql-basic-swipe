package com.dosan.sqlite_practice.ulitscrud

class Alumno(
    id: Int,
    nombre: String,
    edad: Int,
    tipo: String,
    mayor_edad: Int
) {
    var id: Int? = 0
    var nombre: String? = null
    var edad: Int? = 0
    var tipo: String? = ""
    var mayor_edad: Int = 0

    init {
        this.id = id
        this.nombre = nombre
        this.edad = edad
        this.tipo = tipo
        this.mayor_edad = mayor_edad
    }
}