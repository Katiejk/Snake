package com.kkarabet.snake

import kotlin.random.Random

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

    fun getSpeedValue(i:String):Int{
        var speed = 0
        when (i) {
            "Painfully slow" ->
                speed = 7
            "Very slow" ->
                speed =25
            "Slow" ->
                speed = 35
            "Normal" ->
                speed = 50
            "Fast" ->
                speed = 60
            "Very fast" ->
                speed = 70
            "Impossible" ->
                speed = 150
        }
        return speed
    }

    fun getSnakeImage(i:Int):Int{
        var value:Int
        if(i < 7){
            value = i+1
        }else{
            value = 0
        }
        return value
    }

    fun getFoodX():Int{
        return Random.nextInt(-830,830)
    }
    fun getFoodY():Int{
        return Random.nextInt(-675,675)
    }

    companion object{
        var food = 2
        var color = 4
    }
}

