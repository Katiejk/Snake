package com.kkarabet.snake.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kkarabet.snake.R
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cards.view.*

class ResultsFragment : Fragment() {

    companion object {
        fun newInstance() = ResultsFragment()
    }

    private lateinit var viewModel: ResultsViewModel
    private lateinit var textView: TextView
    private lateinit var gameOver:ImageView
    private lateinit var newGame:ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.results_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.findViewById(R.id.textView4)
        gameOver = view.findViewById(R.id.imageView6)
        newGame = view.findViewById(R.id.imageButton5)
        arguments?.let { args ->
            val safeArgs = ResultsFragmentArgs.fromBundle(args)
            val score = safeArgs.finalScore
            textView.setText(score.toString())
        }
        imageAnimation(gameOver)
        newGame.setOnClickListener(){
            view.findNavController().navigate(R.id.action_resultsFragment_to_mainFragment)
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ResultsViewModel::class.java)
    }

    fun imageAnimation(image: ImageView){
        val left =
            ObjectAnimator.ofFloat(image, "rotation", -10f, 10f)

        val right =
            ObjectAnimator.ofFloat(image, "rotation", 10f, -10f)

        right.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                imageAnimation(image)
            }
        })


        val set = AnimatorSet()
        set.play(left).before(right)
        set.duration = 1000L
        set.start()
    }
}