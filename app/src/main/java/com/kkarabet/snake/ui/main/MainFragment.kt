package com.kkarabet.snake.ui.main

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.findNavController
import com.kkarabet.snake.R
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() {

    private lateinit var welcome: ImageView
    private lateinit var settingsButton: ImageButton
    private lateinit var startButton: ImageButton
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        welcome = view.imageView
        settingsButton = view.imageButton2
        startButton = view.imageButton
        imageAnimation(welcome)

        settingsButton.setOnClickListener(){
            view.findNavController().navigate(R.id.mainToSettings)
        }
        startButton.setOnClickListener(){
            view.findNavController().navigate(R.id.action_mainFragment_to_configFragment)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


    }

    fun imageAnimation(image:ImageView){
        val big =
            ObjectAnimator.ofFloat(image, "scaleY", 0.75f, 1.5f)

        val small =
            ObjectAnimator.ofFloat(image, "scaleY", 1.5f, 0.75f)


        val bigx =
            ObjectAnimator.ofFloat(image, "scaleX", 0.75f, 1.5f)

        val smallx =
            ObjectAnimator.ofFloat(image, "scaleX", 1.5f, 0.75f)


        small.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                imageAnimation(image)
            }
        })


        val set = AnimatorSet()
        set.play(big).before(small)
        set.play(bigx).with(big)
        set.play(smallx).with(small)
        set.duration = 750L
        set.start()
    }


}
