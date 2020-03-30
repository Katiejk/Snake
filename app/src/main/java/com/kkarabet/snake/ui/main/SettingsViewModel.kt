package com.kkarabet.snake.ui.main
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kkarabet.snake.GameModel

class SettingsViewModel (application: Application) : AndroidViewModel(application){
    private val model = GameModel(getApplication<Application>().assets)
    var color = GameModel.color
    var food = GameModel.food

    fun updateColor(newColor:Int){
        GameModel.color = newColor
    }
    fun updateFood(newFood:Int){
        GameModel.food = newFood
    }

    fun playSettingsSound() {
        model.playClose()
    }
}