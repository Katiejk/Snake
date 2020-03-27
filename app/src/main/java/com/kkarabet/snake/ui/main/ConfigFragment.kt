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
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kkarabet.snake.R
import kotlinx.android.synthetic.main.cards.view.*

class ConfigFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigFragment()
    }

    private lateinit var viewModel: ConfigViewModel
    private lateinit var reduceReuseRecycle:RecyclerView
    private lateinit var textView:TextView
    private lateinit var start: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.config_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  ViewModelProviders.of(this).get(ConfigViewModel::class.java)
        textView = view.findViewById(R.id.textView3)
        reduceReuseRecycle = view.findViewById(R.id.recyclerView)
        reduceReuseRecycle.layoutManager = LinearLayoutManager(context)
        reduceReuseRecycle.adapter  = Adapter(viewModel.speeds)
        start = view.findViewById(R.id.imageButton4)
        imageAnimation(start)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConfigViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var speed:String
        private val speedTextView: TextView = itemView.speedTitle
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            viewModel.getSpeed(viewModel.speeds.indexOf(speed))
            textView.setText(viewModel.speed.value)

        }

        fun bind(speed:String) {
            this.speed = speed
            speedTextView.text = speed
        }
    }

    private inner class Adapter(private val speed: List<String>) :
        RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.cards, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = viewModel.speeds.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(viewModel.speeds[position])
        }
    }
    fun imageAnimation(image:ImageButton){
        val big =
            ObjectAnimator.ofFloat(image, "scaleY", 0.75f, 1f)

        val small =
            ObjectAnimator.ofFloat(image, "scaleY", 1f, 0.75f)


        val bigx =
            ObjectAnimator.ofFloat(image, "scaleX", 0.75f, 1f)

        val smallx =
            ObjectAnimator.ofFloat(image, "scaleX", 1f, 0.75f)


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