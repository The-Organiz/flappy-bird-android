package com.example.flappy_bird

//import android.R
//import android.R

import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set background
        setContentView(R.layout.activity_main)

        // move background
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

        // animate flapping bird
        val img = findViewById<View>(R.id.bird_fly) as ImageView
        img.setBackgroundResource(R.drawable.bird_fly_animation)
        val frameAnimation = img.background as AnimationDrawable
        frameAnimation.start()

        // animate bouncing bird
        val animUpDown = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.up_down
        )
        img.startAnimation(animUpDown)
    }
}