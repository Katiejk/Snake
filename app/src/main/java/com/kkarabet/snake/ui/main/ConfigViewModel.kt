package com.kkarabet.snake.ui.main
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kkarabet.snake.GameModel

class ConfigViewModel(application: Application) : AndroidViewModel(application) {

    private val model = GameModel(getApplication<Application>().assets)
    val speeds = model.speeds
    private val speedMutable = MutableLiveData<String>()
    var speed: LiveData<String> = speedMutable

    fun getSpeed(i:Int) {
        val thisSpeed:String
        thisSpeed = model.getSpeed(i)
        speedMutable.value = thisSpeed
    }

    fun playStartSound() {
        model.playStart()
    }


}