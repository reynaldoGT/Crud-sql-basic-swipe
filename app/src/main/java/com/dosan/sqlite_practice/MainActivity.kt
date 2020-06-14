package com.dosan.sqlite_practice

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dosan.sqlite_practice.events.ClickListener
import com.dosan.sqlite_practice.events.LongClickListener
import com.dosan.sqlite_practice.ulitscrud.Alumno
import com.dosan.sqlite_practice.ulitscrud.AlumnoCrud
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var lista: RecyclerView? = null
    var adaptador: AdaptadorCustom? = null
    var layout_Manager: RecyclerView.LayoutManager? = null
    var alumnos: ArrayList<Alumno>? = null
    var crud: AlumnoCrud? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lista = findViewById(R.id.lista)
        lista?.setHasFixedSize(true)

        layout_Manager = LinearLayoutManager(this)
        // definir el color y el icono en el swipe
        val colorDrawableBackground = ColorDrawable(Color.parseColor("#b71c1c"))
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.i_delete)!!

        lista?.layoutManager = layout_Manager

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                //(adaptador as AdaptadorCustom).removeItem(viewHolder)
//                Toast.makeText(this,alumnos!![position].id.toString(),Toast)
                //alumnos!![position].id.toString()
                val listPosition = viewHolder.adapterPosition
                val idItem = alumnos!![listPosition].id
                //Toast.makeText(applicationContext,alumnos!![listPosition].id.toString() , Toast.LENGTH_SHORT).show()

                crud?.deleteAlumno(idItem!!)

                (adaptador as AdaptadorCustom).removeItem(viewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                if (dX > 0) {
                    colorDrawableBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    deleteIcon.setBounds(itemView.left + iconMarginVertical, itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth, itemView.bottom - iconMarginVertical)
                } else {
                    colorDrawableBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    deleteIcon.setBounds(itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth, itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical, itemView.bottom - iconMarginVertical)
                    deleteIcon.level = 0
                }

                colorDrawableBackground.draw(c)

                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                deleteIcon.draw(c)

                c.restore()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(lista)

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            startActivity(Intent(this, NuevoAlumno::class.java))
        }

        crud = AlumnoCrud(this)

        alumnos = crud?.getAlumnos()
        adaptador = AdaptadorCustom(alumnos!!, object : ClickListener {
            override fun onclick(vista: View, index: Int) {
                //CLick listener
                val intent = Intent(applicationContext, DetalleAlumno::class.java)
                intent.putExtra("ID", alumnos!!.get(index).id)
                startActivity(intent)
            }
        }, object : LongClickListener {
            override fun longClik(vista: View, index: Int) {
            }
        })

        lista?.adapter = adaptador

    }
}