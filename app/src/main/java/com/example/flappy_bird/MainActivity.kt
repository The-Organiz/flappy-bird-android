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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set moving background
        setContentView(R.layout.activity_main)
        moveBackground()
        // animate static bird
        val bird = bounceBird(flapBird())
    }

    fun moveBackground(){
        val backgroundOne = findViewById<ImageView>(R.id.background1);
        val backgroundTwo = findViewById<ImageView>(R.id.background2);
        val animator = ValueAnimator.ofFloat(0.0f, -1.0f) // change sign for reverse
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width = backgroundOne.width.toFloat()
            val translationX = width * progress
            backgroundOne.translationX = translationX
            backgroundTwo.translationX = translationX + width // change to - for reverse
        }
        animator.start()
    }

    fun flapBird(): ImageView {
        val img = findViewById<View>(R.id.bird_fly) as ImageView
        img.setBackgroundResource(R.drawable.bird_fly_animation)
        val frameAnimation = img.background as AnimationDrawable
        frameAnimation.start()
        return img
    }

    fun bounceBird(img: ImageView): ImageView{
        val animUpDown = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.up_down
        )
        img.startAnimation(animUpDown)
        return img
    }
}