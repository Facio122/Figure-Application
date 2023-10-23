package com.example.figureapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val toolbar = initToolbar()


        toolbar.setNavigationOnClickListener{
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview = item.itemId
        val table = findViewById<TableLayout>(R.id.myTable)

        when(itemview) {
            R.id.add -> {
                Toast.makeText(applicationContext, "Add", Toast.LENGTH_SHORT).show()
                addTableRow(table)
            }
            R.id.remove -> {
                Toast.makeText(applicationContext,"Remove",Toast.LENGTH_SHORT).show()
                removeTableRow(table)
            }
        }

        return false
    }

    private fun initToolbar (): androidx.appcompat.widget.Toolbar
    {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.mipmap.ic_menu))
        return toolbar
    }

    private fun addTableRow(table: TableLayout)
    {
        val tr = layoutInflater.inflate(R.layout.field, null)
        table.addView(tr)
    }

    private fun removeTableRow(table: TableLayout)
    {
        table.removeViewAt(table.childCount - 1)
        //Toast.makeText(applicationContext,table.childCount.toString(),Toast.LENGTH_SHORT).show()
    }




}
