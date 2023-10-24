package com.example.figureapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.figureapplication.ui.theme.Figure

@Suppress("DEPRECATION")
class Statistics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statistics)

        val toolbar = initToolbar()

        val receivedBundle = intent.extras
        if(receivedBundle != null)
        {
            val serializablefiguresList = receivedBundle.getSerializable("figuresList")
            if(serializablefiguresList != null)
            {
                if(serializablefiguresList is List<*>)
                {
                    val figuresList = serializablefiguresList.filterIsInstance<Figure>()
                    setDataInViews(figuresList)
                }
            }
        }
    }


    private fun initToolbar (): androidx.appcompat.widget.Toolbar {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        return toolbar
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statistic_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview = item.itemId

        when(itemview) {
            R.id.home -> {
                finish()
            }
        }
        return false
    }

    private fun setDataInViews(figures: List<Figure>)
    {
        val squareQuantView = findViewById<TextView>(R.id.textQuantSquare)
        val squareAttrView = findViewById<TextView>(R.id.textAttrSquare)
        val squareFieldView = findViewById<TextView>(R.id.textFieldSquare)
        val squaresList = figures.filter { it.type == 0 }
        val squareQuantValue = squaresList.count()
        val squareAttrSum = squaresList.sumByDouble { it.attr }
        val squareFieldSum = squaresList.sumByDouble { it.field }
        squareQuantView.text = squareQuantValue.toString()
        squareAttrView.text = String.format("%.2f",squareAttrSum)
        squareFieldView.text = String.format("%.2f",squareFieldSum)

        val triangleQuantView = findViewById<TextView>(R.id.textQantTriangle)
        val triangleAttrView = findViewById<TextView>(R.id.textAttrTriangle)
        val triangleFieldView = findViewById<TextView>(R.id.textFieldTriangle)
        val triangleList = figures.filter { it.type == 1 }
        val triangleQuantValue = triangleList.count()
        val triangleAttrSum = triangleList.sumByDouble { it.attr }
        val triangleFieldSum = triangleList.sumByDouble { it.field }
        triangleQuantView.text = triangleQuantValue.toString()
        triangleAttrView.text = String.format("%.2f",triangleAttrSum)
        triangleFieldView.text = String.format("%.2f",triangleFieldSum)

        val circleQuantView = findViewById<TextView>(R.id.textQantCircle)
        val circleAttrView = findViewById<TextView>(R.id.textAttrCircle)
        val circleFieldView = findViewById<TextView>(R.id.textFieldCircle)
        val circleList = figures.filter { it.type == 2 }
        val circleQuantValue = circleList.count()
        val circleAttrSum = circleList.sumByDouble { it.attr }
        val circleFieldSum = circleList.sumByDouble { it.field }
        circleQuantView.text = circleQuantValue.toString()
        circleAttrView.text = String.format("%.2f",circleAttrSum)
        circleFieldView.text = String.format("%.2f",circleFieldSum)
    }

}