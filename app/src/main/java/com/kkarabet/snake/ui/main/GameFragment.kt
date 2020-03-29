package com.kkarabet.snake.ui.main

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.kkarabet.snake.R

class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private lateinit var viewModel: GameViewModel
    private lateinit var snake:ImageView
    private lateinit var up:ImageButton
    private lateinit var down:ImageButton
    private lateinit var left:ImageButton
    private lateinit var right:ImageButton
    private lateinit var food:ImageView
    private lateinit var score:TextView
    private lateinit var gameOver:Button
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.game_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        snake = view.findViewById(R.id.imageView3)
        up = view.findViewById(R.id.up)
        down = view.findViewById(R.id.down)
        left = view.findViewById(R.id.left)
        right = view.findViewById(R.id.right)
        food = view.findViewById(R.id.imageView5)
        score = view.findViewById(R.id.textView5)
        gameOver = view.findViewById(R.id.button2)

        arguments?.let { args ->
            val safeArgs = GameFragmentArgs.fromBundle(args)
            val speed = safeArgs.speedSelected
            viewModel.getSpeedValue(speed)
        }

        viewModel.getFood()
        when(viewModel.food.value){
            0->
                food.setImageResource(R.drawable.mouse)
            1 ->
                food.setImageResource(R.drawable.junk)
            2 ->
                food.setImageResource(R.drawable.apple)

        }
        viewModel.setDirection(0)
        viewModel.setSnakeX(0)
        viewModel.setSnakeY(0)
        viewModel.getNextSnakeImage(6)
        viewModel.setTrue()

        turn(3,0,snake,food,up,down,left,right)
        zoomZoomUp(snake,food)
        moveFood(food)
        imageAnimation(food)
        viewModel.initializeScore()
        score.setText(viewModel.score.value!!.toString())

        up.setOnClickListener(){
            turn(viewModel.direction.value!!,0,snake,food,up,down,left,right)

        }
        down.setOnClickListener(){
            turn(viewModel.direction.value!!,2,snake,food,up,down,left,right)
        }
        left.setOnClickListener(){
            turn(viewModel.direction.value!!,3,snake,food,up,down,left,right)
        }
        right.setOnClickListener(){
            turn(viewModel.direction.value!!,1,snake,food,up,down,left,right)
        }

        gameOver.setOnClickListener { button: View ->
            if(viewModel.gameOver.value!! ==0 ){
                var scoreFinal : Int? = viewModel.score.value!!
                scoreFinal?.let {
                    val finalScore = viewModel.score.value!!
                    val actionThing = GameFragmentDirections.actionGameFragmentToResultsFragment(finalScore)
                    Navigation.findNavController(button).navigate(actionThing)
                }
            }
            viewModel.setFalse()

        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    fun moveFood(food:ImageView){
        viewModel.getNewFood()
        val foodY =
            ObjectAnimator.ofFloat(food, "translationY", viewModel.foodY.value!!.toFloat(),viewModel.foodY.value!!.toFloat())
        val foodX =
            ObjectAnimator.ofFloat(food, "translationX", viewModel.foodX.value!!.toFloat(),viewModel.foodX.value!!.toFloat())
        ObjectAnimator.ofFloat(food, "translationX", 900f,900f)
        foodY.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {food.setVisibility(View.INVISIBLE)}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                food.setVisibility(View.VISIBLE)
            }
        })

        val set = AnimatorSet()
        set.play(foodY).with(foodX)
        set.duration = 100L
        set.start()

    }

    fun zoomZoomUp(snake:ImageView,food:ImageView){
        val snakeAnim =
            ObjectAnimator.ofFloat(snake, "translationY", viewModel.snakeY.value!!.toFloat(), viewModel.snakeY.value!!.toFloat()-viewModel.speedVal.value!!)
        snakeAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if(viewModel.direction.value!! == 0){
                    viewModel.setSnakeY(viewModel.snakeY.value!!-viewModel.speedVal.value!!)
                    slither(snake,food)
                    zoomZoomUp(snake,food)
                }
            }
        })

        snakeAnim.duration=150L
        snakeAnim.start()
    }

    fun zoomZoomRight(snake:ImageView,food:ImageView){
        val snakeAnim =
            ObjectAnimator.ofFloat(snake, "translationX", viewModel.snakeX.value!!.toFloat(), viewModel.snakeX.value!!+viewModel.speedVal.value!!.toFloat())
        snakeAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if(viewModel.direction.value!! == 1){
                    viewModel.setSnakeX(viewModel.snakeX.value!!+viewModel.speedVal.value!!)
                    slither(snake,food)
                    zoomZoomRight(snake,food)
                }
            }
        })

        snakeAnim.duration = 150L
        snakeAnim.start()
    }

    fun zoomZoomDown(snake:ImageView,food:ImageView){
        val snakeAnim =
            ObjectAnimator.ofFloat(snake, "translationY", viewModel.snakeY.value!!.toFloat(), viewModel.snakeY.value!!+viewModel.speedVal.value!!.toFloat())

        snakeAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if(viewModel.direction.value!! == 2){
                    viewModel.setSnakeY(viewModel.snakeY.value!!+viewModel.speedVal.value!!)
                    slither(snake,food)
                    zoomZoomDown(snake,food)
                }
            }
        })

        snakeAnim.duration = 150L
        snakeAnim.start()
    }
    fun zoomZoomLeft(snake:ImageView,food: ImageView){
        val snakeAnim =
            ObjectAnimator.ofFloat(snake, "translationX", viewModel.snakeX.value!!.toFloat(), viewModel.snakeX.value!!.toFloat()-viewModel.speedVal.value!!)
        snakeAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                if(viewModel.direction.value!! == 3){
                    viewModel.setSnakeX(viewModel.snakeX.value!!-viewModel.speedVal.value!!)
                    slither(snake,food)
                    zoomZoomLeft(snake,food)
                }
            }
        })
        snakeAnim.duration = 150L
        snakeAnim.start()
    }

    fun turn(currentDirection:Int,newDirection:Int,snake:ImageView,food: ImageView,up:ImageView,down:ImageView,left:ImageView,right:ImageView){
        if(currentDirection == 0 && newDirection == 1){
            val animator = ValueAnimator.ofFloat(25f, 115f)
            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }
            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(1)
            zoomZoomRight(snake,food)
            inflate(right)
            deflate(up)
            deflate(down)
            deflate(left)
        }else if(currentDirection == 0 && newDirection ==3){
            val animator = ValueAnimator.ofFloat(25f, -65f)

            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }

            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(3)
            zoomZoomLeft(snake,food)
            inflate(left)
            deflate(up)
            deflate(down)
            deflate(right)
        }else if(currentDirection == 1 && newDirection ==0){
            val animator = ValueAnimator.ofFloat(115f, 25f)

            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }

            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(0)
            zoomZoomUp(snake,food)
            inflate(up)
            deflate(right)
            deflate(down)
            deflate(left)
        }else if(currentDirection == 1 && newDirection ==2){
            val animator = ValueAnimator.ofFloat(115f, 205f)

            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }

            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(2)
            zoomZoomDown(snake,food)
            inflate(down)
            deflate(up)
            deflate(right)
            deflate(left)

        }else if(currentDirection == 2 && newDirection ==1){
            val animator = ValueAnimator.ofFloat(205f, 115f)

            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }

            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(1)
            zoomZoomRight(snake,food)
            inflate(right)
            deflate(up)
            deflate(down)
            deflate(left)

        }else if(currentDirection == 2 && newDirection ==3){
            val animator = ValueAnimator.ofFloat(205f, 295f)

            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }

            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(3)
            zoomZoomLeft(snake,food)
            inflate(left)
            deflate(up)
            deflate(down)
            deflate(right)

        }else if(currentDirection == 3 && newDirection ==2){
            val animator = ValueAnimator.ofFloat(295f, 205f)

            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }

            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(2)
            zoomZoomDown(snake,food)
            inflate(down)
            deflate(up)
            deflate(right)
            deflate(left)

        }else if(currentDirection == 3 && newDirection ==0){
            val animator = ValueAnimator.ofFloat(295f, 385f)

            animator.addUpdateListener {
                val value = it.animatedValue as Float
                snake.rotation= value
            }

            animator.interpolator = DecelerateInterpolator()
            animator.duration = 100L
            animator.start()
            viewModel.setDirection(0)
            zoomZoomUp(snake,food)
            inflate(up)
            deflate(right)
            deflate(down)
            deflate(left)

        }
    }

    fun slither(snake:ImageView,food: ImageView){
        viewModel.getNextSnakeImage(viewModel.snakeImage.value!!)
        viewModel.getColor()
        if(viewModel.snakeY.value!!-viewModel.foodY.value!! > -85 && viewModel.snakeY.value!!-viewModel.foodY.value!! < 85
            && viewModel.snakeX.value!!-viewModel.foodX.value!! > -85 && viewModel.snakeX.value!!-viewModel.foodX.value!! < 85){
            viewModel.updateScore()
            score.setText(viewModel.score.value!!.toString())
            moveFood(food)

        }

        if(viewModel.snakeY.value!! > 675 || viewModel.snakeY.value!! < -675
            || viewModel.snakeX.value!! > 830 || viewModel.snakeX.value!! < -830){
            gameOver.performClick()
        }

        when(viewModel.color.value){
            0->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeonered)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwored)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreered)
                    3 ->
                        snake.setImageResource(R.drawable.snakefourred)
                    4 ->
                        snake.setImageResource(R.drawable.snakefivered)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixred)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevenred)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightred)
                }
            1 ->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeoneorange)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwoorange)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreeorange)
                    3 ->
                        snake.setImageResource(R.drawable.snakefourorange)
                    4 ->
                        snake.setImageResource(R.drawable.snakefiveorange)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixorange)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevenorange)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightorange)
                }
            2 ->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeoneyellow)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwoyellow)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreeyellow)
                    3 ->
                        snake.setImageResource(R.drawable.snakefouryellow)
                    4 ->
                        snake.setImageResource(R.drawable.snakefiveyellow)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixyellow)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevenyellow)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightyellow)
                }
            3 ->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeonegreen)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwogreen)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreegreen)
                    3 ->
                        snake.setImageResource(R.drawable.snakefourgreen)
                    4 ->
                        snake.setImageResource(R.drawable.snakefivegreen)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixgreen)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevengreen)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightgreen)
                }
            4 ->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeone)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwo)
                    2 ->
                        snake.setImageResource(R.drawable.snakethree)
                    3 ->
                        snake.setImageResource(R.drawable.snakefour)
                    4 ->
                        snake.setImageResource(R.drawable.snakefive)
                    5 ->
                        snake.setImageResource(R.drawable.snakesix)
                    6 ->
                        snake.setImageResource(R.drawable.snakeseven)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeight)
                }
            5 ->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeonelblue)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwolblue)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreelblue)
                    3 ->
                        snake.setImageResource(R.drawable.snakefourlblue)
                    4 ->
                        snake.setImageResource(R.drawable.snakefivelblue)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixlblue)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevenlblue)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightlblue)
                }
            6 ->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeoneblue)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwoblue)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreeblue)
                    3 ->
                        snake.setImageResource(R.drawable.snakefourblue)
                    4 ->
                        snake.setImageResource(R.drawable.snakefiveblue)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixblue)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevenblue)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightblue)
                }
            7 ->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeonepurple)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwopurple)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreepurple)
                    3 ->
                        snake.setImageResource(R.drawable.snakefourpurple)
                    4 ->
                        snake.setImageResource(R.drawable.snakefivepurple)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixpurple)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevenpurple)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightpurple)
                }
            8->
                when(viewModel.snakeImage.value){
                    0->
                        snake.setImageResource(R.drawable.snakeoneblack)
                    1 ->
                        snake.setImageResource(R.drawable.snaketwoblack)
                    2 ->
                        snake.setImageResource(R.drawable.snakethreeblack)
                    3 ->
                        snake.setImageResource(R.drawable.snakefourblack)
                    4 ->
                        snake.setImageResource(R.drawable.snakefiveblack)
                    5 ->
                        snake.setImageResource(R.drawable.snakesixblack)
                    6 ->
                        snake.setImageResource(R.drawable.snakesevenblack)
                    7 ->
                        snake.setImageResource(R.drawable.snakeeightblack)
                }
        }

    }

    fun imageAnimation(image:ImageView){
        val big =
            ObjectAnimator.ofFloat(image, "scaleY", 0.90f, 1.1f)

        val small =
            ObjectAnimator.ofFloat(image, "scaleY", 1.1f, 0.9f)


        val bigx =
            ObjectAnimator.ofFloat(image, "scaleX", 0.9f, 1.1f)

        val smallx =
            ObjectAnimator.ofFloat(image, "scaleX", 1.1f, 0.9f)


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
        set.duration = 350L
        set.start()
    }

    fun inflate(arrow: ImageView){
        val animator = ValueAnimator.ofFloat(0.6f, 1f)

        animator.addUpdateListener {
            val value = it.animatedValue as Float
            arrow.alpha= value
        }

        animator.interpolator = DecelerateInterpolator()
        animator.duration = 100L
        animator.start()
    }

    fun deflate(arrow: ImageView){
        val animator = ValueAnimator.ofFloat(1f,0.6f)

        animator.addUpdateListener {
            val value = it.animatedValue as Float
            arrow.alpha= value
        }

        animator.interpolator = DecelerateInterpolator()
        animator.duration = 100L
        animator.start()
    }
}