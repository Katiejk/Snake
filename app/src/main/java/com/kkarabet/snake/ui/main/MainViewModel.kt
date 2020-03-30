package com.kkarabet.snake.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kkarabet.snake.GameModel

class MainViewModel (application: Application) : AndroidViewModel(application){
    private val model = GameModel(getApplication<Application>().assets)

    fun playStartSound() {
        model.playBeep()
    }

    fun playSettingsSound() {
        model.playOpen()
    }
}
