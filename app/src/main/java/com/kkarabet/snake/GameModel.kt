package com.kkarabet.snake

class GameModel {

    var speeds = listOf(
        "Painfully slow",
        "Very slow",
        "Slow",
        "Normal",
        "Fast",
        "Very fast",
        "Impossible")

    fun getSpeed(i:Int):String{
        return speeds[i]
    }

    companion object{
        var food = 2
        var color = 4
    }
}

