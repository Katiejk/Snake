package com.kkarabet.snake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.kkarabet.snake.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }
    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.navHost).navigateUp()

}
