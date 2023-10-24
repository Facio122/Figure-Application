package com.example.figureapplication.ui.theme

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.lang.Math.pow
import java.lang.Math.sqrt

open class Figure(): Parcelable {

    var field = 0.0;
    var attr = 0.0;
    var type = 0;

    constructor(parcel: Parcel) : this() {
        field = parcel.readDouble()
        attr = parcel.readDouble()
        type = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(field)
        parcel.writeDouble(attr)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    @SuppressLint("ParcelCreator")
    companion object CREATOR : Parcelable.Creator<Figure> {
        override fun createFromParcel(parcel: Parcel): Figure {
            return Figure(parcel)
        }

        override fun newArray(size: Int): Array<Figure?> {
            return arrayOfNulls(size)
        }
    }


}

class Square(val side: Double) : Figure()
{
    @JvmName("setSquareType")
    fun setType()
    {
        type = 0
    }

    @JvmName("getSquareField")
     fun calculateField()
    {
        field = (side * side)
    }
    @JvmName("getSquareDiagonal")
     fun calculateDiagonal()
    {
        attr = sqrt(2.0) * side
    }
}

class Triangle(val side: Double) : Figure()
{
    @JvmName("setTriangleType")
    fun setType()
    {
        type = 1
    }

    @JvmName("getTriangleField")
    fun calculateField()
    {
        field = (pow(side,2.0)* sqrt(3.0))/4
    }
    @JvmName("getTriangleHeight")
    fun calculateHeight()
    {
        attr = (sqrt(3.0) * side)/2
    }
}

class Circle(val side: Double) : Figure()
{
    @JvmName("setCircleType")
    fun setType()
    {
        type = 2
    }

    @JvmName("getCircleField")
    fun calculateField()
    {
        field = pow(side,2.0)
    }
    @JvmName("getCircleDiameter")
    fun calculateDiameter()
    {
        attr = side * 2
    }
}
