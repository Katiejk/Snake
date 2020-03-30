package com.kkarabet.snake

import android.content.ContentValues.TAG
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import kotlin.random.Random

class GameModel(private val assets: AssetManager){

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
                speed = 65
            "Very fast" ->
                speed = 77
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

    data class Sound(val path: String, var sndId: Int? = null) {

    }

    val sounds: List<Sound>
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    init {
        sounds = loadSounds()
    }

    fun release() = soundPool.release()

    fun play(sound: Sound) {
        sound.sndId?.let {
            soundPool.play(
                it,
                1f,
                1f,
                1,
                0,
                1f)
        }
    }

    private fun loadSounds(): List<Sound> {
        var files = emptyArray<String>()
        try {
            assets.list("Sounds")?.let {
                files = it
            }
        } catch (e: Exception) {
            Log.d(TAG, "Couldn't list sound assets!", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        val filenamePath = "Sounds"
        files.forEach { filename ->
            val assetPath = "$filenamePath/$filename"
            if (assetPath.contains(".wav")) {
                val sound = Sound(assetPath)
                try {
                    val afd: AssetFileDescriptor = assets.openFd(sound.path)
                    sound.sndId = soundPool.load(afd, 1)
                    sounds.add(sound)
                } catch (ioe: IOException) {
                    Log.e(TAG, "Couldn't load sound: $filename"
                        , ioe)
                }
            }
        }
        return sounds
    }

    fun playClick(){
        play(sounds[0])
    }
    fun playBeep(){
        play(sounds[1])
    }
    fun playEat(){
        play(sounds[2])
    }
    fun playClose(){
        play(sounds[3])
    }
    fun playGameOver(){
        play(sounds[4])
    }
    fun playStart(){
        play(sounds[5])
    }
    fun playOpen(){
        play(sounds[6])
    }

    companion object{
        var food = 2
        var color = 4
    }
}

