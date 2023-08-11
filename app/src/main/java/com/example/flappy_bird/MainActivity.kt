package com.example.flappy_bird

import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var bird: ImageView;
    private var started: Boolean = false;
    private var posY: Float = 0f;
    private var dy: Float = 0.05f;
    private val ddy: Float = 0.01f;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set moving background
        setContentView(R.layout.activity_main)
        moveGround()
        // animate static bird
        bird = bounceBird(flapBird())
    }

    private fun moveGround(){
        val ground1 = findViewById<ImageView>(R.id.ground1);
        val ground2 = findViewById<ImageView>(R.id.ground2);
        val animator = ValueAnimator.ofFloat(0.0f, -1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 2500L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width = ground1.width.toFloat()
            val translationX = width * progress
            ground1.translationX = translationX
            ground2.translationX = translationX + width
        }
        animator.start()
    }

    private fun flapBird(): ImageView {
        val img = findViewById<View>(R.id.bird_fly) as ImageView
        img.setBackgroundResource(R.drawable.bird_fly_animation)
        val frameAnimation = img.background as AnimationDrawable
        frameAnimation.start()
        return img
    }

    private fun bounceBird(img: ImageView): ImageView{
        val animUpDown = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.up_down
        )
        img.startAnimation(animUpDown)
        return img
    }

    fun screenTapped(view: View?) {
        bird = findViewById<ImageView>(R.id.bird_fly);

        val animator = ValueAnimator.ofFloat(1f, 0.0f)
        animator.interpolator = LinearInterpolator()
        animator.duration = 1000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val height = bird.height.toFloat()
            val translationY = height * progress
            bird.translationY = translationY
        }
        animator.start()

        if(!started) {
            started = true
            gameLoop()
        }
    }

    private fun gameLoop(){
        var animator = ValueAnimator.ofFloat(0f, 1f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        var end = posY + dy
        animator.addUpdateListener { _ ->
            dropBird(posY, end)
            dy += ddy
            posY = end
            end += dy
        }
        animator.start()
    }

    private fun dropBird(start: Float, end: Float){
        var animator = ValueAnimator.ofFloat(start, end)
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val height = bird.height.toFloat()
            val translationY = height * progress
            bird.translationY = translationY
        }
        animator.start()
    }
}