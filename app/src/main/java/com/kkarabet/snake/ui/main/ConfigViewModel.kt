package com.kkarabet.snake.ui.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkarabet.snake.GameModel

class ConfigViewModel :  ViewModel(){

    private val model = GameModel()
    val speeds = model.speeds
    private val speedMutable = MutableLiveData<String>()
    var speed: LiveData<String> = speedMutable

    fun getSpeed(i:Int) {
        val thisSpeed:String
        thisSpeed = model.getSpeed(i)
        speedMutable.value = thisSpeed
    }


}