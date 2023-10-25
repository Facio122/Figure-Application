package com.example.figureapplication

class SingleTon {
    var minValue: Int = 10
    var maxValue: Int = 15
    var numValue: Int = 8
    private constructor()

    companion object
    {
        private var instance: SingleTon? = null

        fun getInstance() = instance?: synchronized(this)
        {
            instance?: SingleTon().also {
                instance = it
            }
        }
    }
}