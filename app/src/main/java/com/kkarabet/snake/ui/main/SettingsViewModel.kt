package com.kkarabet.snake.ui.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkarabet.snake.GameModel

class SettingsViewModel :  ViewModel(){
    var color = GameModel.color
    var food = GameModel.food

    fun updateColor(newColor:Int){
        GameModel.color = newColor
    }
    fun updateFood(newFood:Int){
        GameModel.food = newFood
    }
}