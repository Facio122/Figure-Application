package com.example.figureapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import com.example.figureapplication.ui.theme.Circle
import com.example.figureapplication.ui.theme.Figure
import com.example.figureapplication.ui.theme.Square
import com.example.figureapplication.ui.theme.Triangle
import java.io.Serializable
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    val figuresList = mutableListOf<Figure>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val toolbar = initToolbar()
        val tabView = findViewById<TableLayout>(R.id.myTable)



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
            R.id.generate -> {
                Toast.makeText(applicationContext,"Generate",Toast.LENGTH_SHORT).show()
                generateFigures(table)
            }
            R.id.GoToStatisticsActivity ->
            {
                val intent = Intent(this, Statistics::class.java)
                val bundle = Bundle()
                bundle.putSerializable("figuresList", figuresList as Serializable)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            R.id.clear ->
            {
                table.removeAllViews()
                figuresList.clear()
            }
        }
        return false
    }

    private fun addTableRow(table: TableLayout)
    {
        val RandomValue = Random.nextInt(0,3)
        val tr = layoutInflater.inflate(R.layout.field, null)
        val img = tr.findViewById<ImageView>(R.id.imageView)
        val attr = tr.findViewById<TextView>(R.id.attrView)
        val field = tr.findViewById<TextView>(R.id.fieldView)
        tr.setOnClickListener{
            val rowIndex = table.indexOfChild(tr)
            tr.setBackgroundColor(resources.getColor(R.color.gray))
            Handler().postDelayed(
                {
                    tr.setBackgroundColor(resources.getColor(R.color.white))
                },500)
        }
        when(RandomValue)
        {
            0 ->
            {
                val square = Square(Random.nextDouble(0.1,10.0))
                square.calculateField()
                square.calculateDiagonal()
                square.setType()

                figuresList.add(square)

                val fieldString = String.format("%.2f",square.field)
                val attrString = String.format("%.2f",square.attr)
                field.text = fieldString
                attr.text = attrString
                img.setImageResource(R.drawable.square)
            }
            1 ->
            {
                val triangle = Triangle(Random.nextDouble(0.1,10.0))
                triangle.calculateField()
                triangle.calculateHeight()
                triangle.setType()

                figuresList.add(triangle)

                val fieldString = String.format("%.2f",triangle.field)
                val attrString = String.format("%.2f",triangle.attr)
                field.text = fieldString
                attr.text = attrString
                img.setImageResource(R.drawable.triangular)
            }
            2 ->
            {
                val circle = Circle(Random.nextDouble(0.1,10.0))
                circle.calculateField()
                circle.calculateDiameter()
                circle.setType()

                figuresList.add(circle)

                val fieldString = String.format("%.2f",circle.field)
                val attrString = String.format("%.2f",circle.attr)
                field.text = fieldString
                attr.text = attrString
                img.setImageResource(R.drawable.circle)
            }

        }
        table.addView(tr)
    }

    private fun removeTableRow(table: TableLayout)
    {
        if(table.childCount != 0)
        {
            table.removeViewAt(table.childCount - 1)
            figuresList.remove(figuresList.last())
        }
    }
    private fun generateFigures(table: TableLayout)
    {
            table.removeAllViews()
            figuresList.clear()
            for(i in 1..Random.nextInt(1,10))
            {
                addTableRow(table)
            }
    }
    private fun initToolbar (): androidx.appcompat.widget.Toolbar {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)
        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.mipmap.ic_menu)
        return toolbar
    }

    private fun indextest(table: TableLayout)
    {
        table.forEachIndexed { index, view ->
            view.tag = index
        }
    }
}
