package com.kkarabet.snake.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.kkarabet.snake.GameModel

class ResultsViewModel  (application: Application) : AndroidViewModel(application){
    private val model = GameModel(getApplication<Application>().assets)

    fun playStartSound() {
        model.playBeep()
    }
}