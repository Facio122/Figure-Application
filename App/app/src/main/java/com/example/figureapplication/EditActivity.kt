package com.example.figureapplication

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initToolbar()
        val minText = findViewById<TextView>(R.id.minValueText)
        val maxText = findViewById<TextView>(R.id.maxValueText)
        val numText = findViewById<TextView>(R.id.numValueText)

        val minSeekbar = findViewById<SeekBar>(R.id.minSlider)
        val maxSeekbar = findViewById<SeekBar>(R.id.maxSlider)
        val numSeekbar = findViewById<SeekBar>(R.id.qantSlider)

        initSliders(minSeekbar,maxSeekbar,numSeekbar,minText,maxText,numText)


    }

    private fun initToolbar (): androidx.appcompat.widget.Toolbar {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.editToolbar)
        setSupportActionBar(toolbar)
        return toolbar
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview = item.itemId

        when(itemview) {
            R.id.home -> {

                finish()
            }
            R.id.apply ->
            {
                sendDataToActivity()
                Toast.makeText(applicationContext,"Apply Changes!",Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    private fun initSliders(minSlider: SeekBar, maxSlider: SeekBar, numSlider: SeekBar,
                            minText: TextView, maxText:TextView, numText:TextView)
    {
        minSlider.progress = 10
        maxSlider.progress = 15
        numSlider.progress = 8

        minText.text = minSlider.progress.toString()
        maxText.text = maxSlider.progress.toString()
        numText.text = numSlider.progress.toString()

        minSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener
        {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                minText.text = progress.toString()
                maxSlider.min = minSlider.progress + 1
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        maxSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                maxText.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        numSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numText.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun sendDataToActivity()
    {
        val minSeekbarVal = findViewById<SeekBar>(R.id.minSlider).progress
        val maxSeekbarVal = findViewById<SeekBar>(R.id.maxSlider).progress
        val numSeebarVal = findViewById<SeekBar>(R.id.qantSlider).progress

        val instance = SingleTon.getInstance()
        instance.minValue = minSeekbarVal
        instance.maxValue = maxSeekbarVal
        instance.numValue = numSeebarVal

    }


}