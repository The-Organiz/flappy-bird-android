package com.example.flappy_bird

import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var bird: ImageView;
    private var started: Boolean = false;
    // negative is up, positive is down
    // negative is clockwise
    private var posY: Float = 0f;
    private var dy: Float = 0.05f;
    private var rotationSpeed = 0.1f
    // constants
    private val gravity: Float = 0.003f;
    private val maxSpeed: Float = 0.05f;
    private val flapSpeed: Float = -0.05f;
    private val rotationAcceleration = 0.01f;
    private val maxRotation = 90f;
    private val initialRotationSpeed = 0.1f;
    private val flapRotation = -80f;
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
        dy = flapSpeed - gravity
        bird.rotation = flapRotation
        rotationSpeed = initialRotationSpeed
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
            if(dy < maxSpeed) {
                dy += gravity
            }
            rotationSpeed += rotationAcceleration
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
            if(bird.rotation < maxRotation) {
                bird.rotation += rotationSpeed
            }
        }
        animator.start()


    }
}