package com.example.figureapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
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
        val table = findViewById<TableLayout>(R.id.myTable)
        sortFigures(table)


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
            R.id.GoToEditActivity ->
            {
                val intent = Intent(this, EditActivity::class.java)
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
        val instance = SingleTon.getInstance()

        val RandomValue = Random.nextInt(0,3)
        val tr = layoutInflater.inflate(R.layout.field, null)
        val img = tr.findViewById<ImageView>(R.id.imageView)
        val attr = tr.findViewById<TextView>(R.id.attrView)
        val field = tr.findViewById<TextView>(R.id.fieldView)
        ClickForTrHandler(tr, table)
        when(RandomValue)
        {
            0 ->
            {
                val square = Square(Random.nextDouble(instance.minValue.toDouble(),instance.maxValue.toDouble()))
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
                val triangle = Triangle(Random.nextDouble(instance.minValue.toDouble(),instance.maxValue.toDouble()))
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
                val circle = Circle(Random.nextDouble(instance.minValue.toDouble(),instance.maxValue.toDouble()))
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
        indexTable(table)
    }

    private fun removeTableRow(table: TableLayout)
    {
        if(table.childCount != 0)
        {
            table.removeViewAt(table.childCount - 1)
            figuresList.remove(figuresList.last())
            indexTable(table)
        }
    }
    private fun generateFigures(table: TableLayout)
    {
            val instance = SingleTon.getInstance()
            table.removeAllViews()
            figuresList.clear()
            for(i in 1..instance.numValue)
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

    private fun ClickForTrHandler(tr: View, table: TableLayout)
    {
        tr.setOnClickListener{
            tr.setBackgroundColor(resources.getColor(R.color.gray))
            Handler().postDelayed(
                {
                    showPopupMenu(tr, table)
                    tr.setBackgroundColor(resources.getColor(R.color.white))
                },500)
        }
    }
    private fun showPopupMenu(view: View, table: TableLayout)
    {
        val popupMenu = PopupMenu(this, view)
        if(popupMenu != null)
        {
            popupMenu.menuInflater.inflate(R.menu.view_menu, popupMenu.menu)
        }
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.removeView -> {
                    val parentView = view.parent as ViewGroup
                    parentView.removeView(view)
                    figuresList.removeAt((view.getTag()  as Int))
                    return@setOnMenuItemClickListener true
                }
                R.id.duplicate ->
                {
                    duplicate(view, table)
                    return@setOnMenuItemClickListener  true
                }

                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
        popupMenu.show()
    }
    private fun indexTable(table: TableLayout)
    {
        table.forEachIndexed{index, view ->
            view.tag = index
        }
    }

    private fun duplicate(view: View, table: TableLayout)
    {
        val index = view.tag as Int
        figuresList.add(figuresList[index])

        val tr = layoutInflater.inflate(R.layout.field,null)

        val imgFrom = view.findViewById<ImageView>(R.id.imageView)
        val img = tr.findViewById<ImageView>(R.id.imageView)
        img.setImageDrawable(imgFrom.drawable)

        val fieldText = tr.findViewById<TextView>(R.id.fieldView)
        fieldText.text = String.format("%.2f",figuresList[index].field)

        val attrText = tr.findViewById<TextView>(R.id.attrView)
        attrText.text = String.format("%.2f",figuresList[index].attr)

        table.addView(tr)
        indexTable(table)
    }

    private fun sortFigures(table: TableLayout)
    {
        val sortByFigureBtn = findViewById<Button>(R.id.sortImage)
        val sortByAttrBtn = findViewById<Button>(R.id.sortAttr)
        val sortByFieldBtn = findViewById<Button>(R.id.sortField)

        sortByFigureBtn.setOnClickListener{
            figuresList.sortBy { it.type }
            table.removeAllViews()
            figuresList.forEach{
                createTableRow(it.type,it.attr,it.field,table)
            }
            indexTable(table)
        }

        sortByAttrBtn.setOnClickListener{
            figuresList.sortBy { it.attr }
            table.removeAllViews()
            figuresList.forEach{
                createTableRow(it.type,it.attr,it.field,table)
            }
            indexTable(table)
        }

        sortByFieldBtn.setOnClickListener{
            figuresList.sortBy { it.field }
            table.removeAllViews()
            figuresList.forEach{
                createTableRow(it.type,it.attr,it.field,table)
            }
            indexTable(table)
        }
    }

    private fun createTableRow(type: Int, attrVal: Double, fieldVal: Double, table:TableLayout)
    {
        val tr = layoutInflater.inflate(R.layout.field, null)
        val img = tr.findViewById<ImageView>(R.id.imageView)
        val attr = tr.findViewById<TextView>(R.id.attrView)
        val field = tr.findViewById<TextView>(R.id.fieldView)

        when(type)
        {
            0 -> img.setImageResource(R.drawable.square)
            1 -> img.setImageResource(R.drawable.triangular)
            2 -> img.setImageResource(R.drawable.circle)
        }
        attr.text = String.format("%.2f",attrVal)
        field.text = String.format("%.2f",fieldVal)

        table.addView(tr)
    }
}
