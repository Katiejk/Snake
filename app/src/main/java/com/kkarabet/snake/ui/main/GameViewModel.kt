package com.kkarabet.snake.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kkarabet.snake.GameModel

class GameViewModel (application: Application) : AndroidViewModel(application){
    private val model = GameModel(getApplication<Application>().assets)
    private val speedValMutable = MutableLiveData<Int>()
    var speedVal: LiveData<Int> = speedValMutable

    fun playEatSound() {
        model.playEat()
    }

    fun playEndSound() {
        model.playGameOver()
    }
    fun playArrowSound() {
        model.playClick()
    }

    fun getSpeedValue(i:String) {
        val value: Int
        value = model.getSpeedValue(i)
        speedValMutable.value = value
    }

    private val directionMutable = MutableLiveData<Int>()
    var direction: LiveData<Int> = directionMutable

    fun setDirection(i: Int) {
        val thisDirection:Int = i
        directionMutable.value = thisDirection
    }

    private val snakeXMutable = MutableLiveData<Int>()
    var snakeX: LiveData<Int> = snakeXMutable

    fun setSnakeX(i: Int) {
        val thisSnakeX:Int = i
        snakeXMutable.value = thisSnakeX
    }

    private val snakeYMutable = MutableLiveData<Int>()
    var snakeY: LiveData<Int> = snakeYMutable

    fun setSnakeY(i: Int) {
        val thisSnakeY:Int = i
        snakeYMutable.value = thisSnakeY
    }

    private val snakeImageMutable = MutableLiveData<Int>()
    var snakeImage: LiveData<Int> = snakeImageMutable

    fun getNextSnakeImage(i: Int) {
        val value: Int
        value = model.getSnakeImage(i)
        snakeImageMutable.value = value
    }

    private val colorMutable = MutableLiveData<Int>()
    var color: LiveData<Int> = colorMutable

    fun getColor() {
        val value: Int
        value = GameModel.color
        colorMutable.value = value
    }

    private val foodMutable = MutableLiveData<Int>()
    var food: LiveData<Int> = foodMutable

    fun getFood() {
        val value: Int
        value = GameModel.food
        foodMutable.value = value
    }

    private val foodXMutable = MutableLiveData<Int>()
    var foodX: LiveData<Int> = foodXMutable

    private val foodYMutable = MutableLiveData<Int>()
    var foodY: LiveData<Int> = foodYMutable

    fun getNewFood() {
        val valueX: Int
        valueX = model.getFoodX()
        foodXMutable.value = valueX
        val valueY: Int
        valueY = model.getFoodY()
        foodYMutable.value = valueY
    }

    private val scoreMutable = MutableLiveData<Int>()
    var score: LiveData<Int> = scoreMutable

    fun updateScore() {
        scoreMutable.value = scoreMutable.value!! + 10
    }
    fun initializeScore() {
        val zero:Int = 0
        scoreMutable.value = zero
    }
    private val gameOverMutable = MutableLiveData<Int>()
    var gameOver: LiveData<Int> = gameOverMutable

    fun setTrue() {
        val value = 0
        gameOverMutable.value = value
    }
    fun setFalse(){
        val value = 1
        gameOverMutable.value = value
    }


}





























